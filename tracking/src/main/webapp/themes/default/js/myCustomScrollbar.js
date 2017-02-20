$(function(){


	  $(".scroll-wrapper-auto").mCustomScrollbar({
          advanced: {
              updateOnContentResize: !1,
              updateOnImageLoad: !1,
              updateOnSelectorChange: !1
          },
          scrollInertia: 200,
          autoHideScrollbar: !1,
          mouseWheel: {
              scrollAmount: 150
          },
          theme: 'minimal-dark',
          callbacks: {
              onScrollStart: function () {
                  $('.webui-popover').removeClass('in'),
                  setTimeout(function () {
                      $('.webui-popover').hide()
                  }, 300)
              },
              onTotalScroll: function () {
//                  t ? (e.find('.mCSB_container').css('top', 0), e.find('.mCSB_dragger').css('top', 0), n())  : s = !0
              }
          }
      });
});

