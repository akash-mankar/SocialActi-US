/*
  $(function() {
	  	
		$('#fileupload').fileupload({submit: function (e, data) {	        
	    	var $this = $(this);
	    	debugger;	    	
	        $.getJSON('/rest/file/url?' + new Date().getTime(), function (result) {
	        	data.url = result.url;	        	
	            $this.fileupload('send', data);	            
	        });
	        return false;
	    }
		}); 	 
	});
  */
  /*
   * jQuery File Upload Plugin JS Example 8.9.0
   * https://github.com/blueimp/jQuery-File-Upload
   *
   * Copyright 2010, Sebastian Tschan
   * https://blueimp.net
   *
   * Licensed under the MIT license:
   * http://www.opensource.org/licenses/MIT
   */

  /*jslint nomen: true, regexp: true */
  /*global $, window, blueimp */

  $(function() {
    /* activate the plugin */
  	$('#fileupload').fileupload({submit: function (e, data) {
          
      	var $this = $(this);
      //	debugger;
          $.getJSON('/rest/file/url?' + new Date().getTime(), function (result) {
          	data.url = result.url;
              $this.fileupload('send', data);
          });          
          return false;
      }
  	});

   
  });
  $(function () {
      'use strict';

      // Initialize the jQuery File Upload widget:
      $('#fileupload').fileupload({
          // Uncomment the following to send cross-domain cookies:
          xhrFields: {withCredentials: true},
    //      url: '/upload?'
      });

      // Enable iframe cross-domain access via redirect option:
      $('#fileupload').fileupload(
          'option',
          'redirect',
          window.location.href.replace(
              /\/[^\/]*$/,
              '/cors/result.html?%s'
          )
      );

      if (window.location.hostname === 'x-y-z-a.appspot.com') {
          // Demo settings:
          $('#fileupload').fileupload('option', {
              url: '//x-y-z-a.appspot.com/',
              // Enable image resizing, except for Android and Opera,
              // which actually support image resizing, but fail to
              // send Blob objects via XHR requests:
              disableImageResize: /Android(?!.*Chrome)|Opera/
                  .test(window.navigator.userAgent),
              maxFileSize: 5000000,
              acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
          });
          // Upload server status check for browsers with CORS support:
          if ($.support.cors) {
              $.ajax({
                  url: '//x-y-z-a.appspot.com/',
                  type: 'HEAD'
              }).fail(function () {
                  $('<div class="alert alert-danger"/>')
                      .text('Upload server currently unavailable - ' +
                              new Date())
                      .appendTo('#fileupload');
              });
          }
      } else {
          // Load existing files:
          $('#fileupload').addClass('fileupload-processing');
          $.ajax({
              // Uncomment the following to send cross-domain cookies:
              //xhrFields: {withCredentials: true},
              url: $('#fileupload').fileupload('option', 'url'),
              dataType: 'json',
              context: $('#fileupload')[0]
          }).always(function () {
              $(this).removeClass('fileupload-processing');
          }).done(function (result) {
              $(this).fileupload('option', 'done')
                  .call(this, $.Event('done'), {result: result});
          });
      }

  });