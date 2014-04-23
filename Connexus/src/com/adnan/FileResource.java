package com.adnan;

/*
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreFailureException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.Lists;
*/

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreFailureException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/file")
public class FileResource {

  private final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  private final BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
  
  /* step 1. get a unique url */
  
  @GET
  @Path("/url")
  public Response getCallbackUrl() {
    /* this is /_ah/upload and it redirects to its given path */
    String url = blobstoreService.createUploadUrl("/rest/file");
    return Response.ok(new FileUrl(url)).build();
  }
  
  /* step 2. post a file */
  
  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response post(@Context HttpServletRequest req,
		@Context HttpServletResponse res) throws IOException,
		URISyntaxException {
     Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
     BlobKey blobKey = blobs.get("files[]");
	
     BlobInfo info = blobInfoFactory.loadBlobInfo(blobKey);
     String name = info.getFilename();
     long size = info.getSize();
     String url = "/rest/file/" + blobKey.getKeyString();

     ImagesService imagesService = ImagesServiceFactory.getImagesService();
     ServingUrlOptions.Builder.withBlobKey(blobKey).crop(true).imageSize(80);
     int sizePreview = 80;
     String urlPreview = imagesService
                .getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)
			.crop(true).imageSize(sizePreview));

     FileMeta meta = new FileMeta(name, size, url, urlPreview);
     UserService userService1 = UserServiceFactory.getUserService();
     User user1 = userService1.getCurrentUser();
     String sName = req.getParameter("streamName");
     Long sId = new Long(req.getParameter("streamId"));
     ConnexusImage im = new ConnexusImage(sId, sName, user1.getEmail(), url) ;
     OfyService.ofy().save().entity(im).now(); 	 
     List<FileMeta> metas = Lists.newArrayList(meta);
     Entity entity = new Entity(metas);
     return Response.ok(entity, MediaType.APPLICATION_JSON).build();
  }
  
  /* step 3. redirected to the meta info */
  
    @GET
    @Path("/{key}/meta")
    public Response redirect(@PathParam("key") String key) throws IOException {
      BlobKey blobKey = new BlobKey(key);
      BlobInfo info = blobInfoFactory.loadBlobInfo(blobKey);

      String name = info.getFilename();
      long size = info.getSize();
      String url = "/rest/file/" + key;

      ImagesService imagesService = ImagesServiceFactory.getImagesService();
      ServingUrlOptions.Builder.withBlobKey(blobKey).crop(true).imageSize(80);
      int sizePreview = 80;
      String urlPreview = imagesService
                .getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)
			.crop(true).imageSize(sizePreview));

      FileMeta meta = new FileMeta(name, size, url, urlPreview);

      List<FileMeta> metas = Lists.newArrayList(meta);
      Entity entity = new Entity(metas);
      return Response.ok(entity,MediaType.APPLICATION_JSON).build();
    }

  /* step 4. download the file */
  
  @GET
  @Path("/{key}")
  public Response serve(@PathParam("key") String key, @Context HttpServletResponse response) throws IOException {
    BlobKey blobKey = new BlobKey(key);
    final BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
    response.setHeader("Content-Disposition", "attachment; filename=" + blobInfo.getFilename());
    BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);
    return Response.ok().build();
  }
  
  /* step 5. delete the file */
  
  @DELETE
  @Path("/{key}")
  public Response delete(@PathParam("key") String key) {
    Status status;
    try {
      blobstoreService.delete(new BlobKey(key));
      status = Status.OK;
    } catch (BlobstoreFailureException bfe) {
      status = Status.NOT_FOUND;
    }
    return Response.status(status).build();
  }
}
