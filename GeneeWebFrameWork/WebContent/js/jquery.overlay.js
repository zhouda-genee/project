/*
 * jQuery Simple Overlay
 * A jQuery Plugin for creating a simple, customizable overlay. Supports multiple instances,
 * custom callbacks, hide on click, glossy effect, and more.
 *
 * Copyright 2011 Tom McFarlin, http://tommcfarlin.com, @moretom
 * Released under the MIT License
 *
 * http://moreco.de/jquery-simple-overlay
 */

(function($) {

  var _opts, _overlay;

	$.fn.overlayshow = function(options) {

    _opts = $.extend({}, $.fn.overlayshow.defaults, options);
    if(container() == 0) {
      _overlay = create(_opts);
      show(_overlay, _opts);
    } 
    
	}; // end overlay

  $.fn.overlayhide = function(){
    if(container() > 0) {
        close(_overlay, _opts);
      }
  }

  /*--------------------------------------------------*
   * helper functions
   *--------------------------------------------------*/
   function container(){
      return $(_opts.container).children("div[class='overlay']").size();
   }
  
  /**
   * Creates the overlay element, applies the styles as specified in the 
   * options, and sets up the event handlers for closing the overlay.
   *
   * opts The plugin's array options.
   */
  function create(opts) {
    // create the overlay and add it to the dom
    var overlay = $('<div></div>')
      .addClass('overlay')
      .css({
        background: opts.color,
        opacity: opts.opacity,
        top: opts.container.toString() === 'body' ? $(opts.container).scrollTop() : $(opts.container).offset().top,
        left: 0,
        width: opts.container === 'body' ? '100%' : $(opts.container).width(),
        //height: opts.container === 'body' ? '100%' : $(opts.container).height(),
        height: $(document).height(),
        position: 'absolute',
        zIndex: 1000,
        display: 'none',
        overflow: 'hidden'
      });

    // add picture
    if (opts.img) {
        var img = $("<div></div>")
          .css({
            "position": "absolute",
            "top":"45%",
            "left":"50%"
          }).append("<img src='"+opts.img+"'/>");
        overlay.append(img);
    }
    

    // if specified, apply the gloss
    if(opts.glossy) {
      applyGloss(opts, overlay);     
    } // end if
    
    // setup the event handlers for closing the overlay
    if(opts.closeOnClick) {
      $(overlay).click(function() {
        close(overlay, opts);
      });
    } // end if
    
    // finally add the overlay
    $(opts.container).append(overlay);
   
    return overlay;
    
  } // end createOverlay
  
  /**
   * Displays the overlay using the effect specified in the options. Optionally
   * triggers the onShow callback function.
   *
   * opts The plugin's array options.
   */
  function show(overlay, opts) {
    
    switch(opts.effect.toString().toLowerCase()) {
    
      case 'fade':
        $(overlay).fadeIn('fast', opts.onShow);
        break;
      
      case 'slide':
        $(overlay).slideDown('fast', opts.onShow);
        break;
        
      default:
        $(overlay).show(opts.onShow);
        break;
    
    } // end switch/case
    
    $(opts.container).css('overflow', 'hidden');
    
  } // end show
  
  /**
   * Hides the overlay using the effect specified in the options. Optionally
   * triggers the onHide callback function.
   *
   * opts The plugin's array options.
   */
  function close(overlay, opts) {
    
    switch(opts.effect.toString().toLowerCase()) {
        
      case 'fade':
        $(overlay).fadeOut('fast', function() {
          if (opts.onHide){
            opts.onHide();
          }
          $(this).remove();
        });
        break;
            
      case 'slide':
        $(overlay).slideUp('fast', function() {
          if (opts.onHide){
            opts.onHide();
          }
          $(this).remove();
        });
        break;
            
      default:
        $(overlay).hide();
        if(opts.onHide) {
          opts.onHide();
        }
        $(overlay).remove();
        break;
            
    } // end switch/case
    
    $(opts.container).css('overflow', 'auto');
    
  } // end close
  
  /**
   * Adds the gloss effect to the overlay.
   *
   * opts     The plugin's options array
   * overlay  The overlay on which the gloss will be applied
   */
  function applyGloss(opts, overlay) {
  
    var gloss = $('<div></div>');
    $(gloss).css({
      background: '#fff',
      opacity: 0.2,
      width: '200%',
      height: '100%',
      position: 'absolute',
      zIndex: 1001,
      msTransform: 'rotate(45deg)',
      webkitTransform: 'rotate(45deg)',
      oTransform: 'rotate(45deg)'
    });
      
    // at the time of development, mozTransform didn't work with >= jQuery 1.5
    if($.browser.mozilla) {
     $(gloss).css('-moz-transform', 'rotate(45deg');
    } // end if
     
    $(overlay).append(gloss);
    
  } // end applyGloss
 
  /*--------------------------------------------------*
   * default settings
   *--------------------------------------------------*/
   
	$.fn.overlayshow.defaults = {
    color: '#FFF',
    opacity: 0.7,
    effect: 'none',
    onShow: null,
    onHide: null,
    closeOnClick: false,
    glossy: false,
    container: 'body',
    img: null
	}; // end defaults

})(jQuery);