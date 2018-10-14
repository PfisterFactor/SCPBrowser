// Called when page is loaded
function onLoad() {
  handleOversizedTables();
  handleOversizedImages();
  handleCollapsibleBlocks();
  handleFootnotes();
  handleYUITabView();


}

Element.prototype.documentOffsetTop = function () {
    return this.offsetTop + ( this.offsetParent ? this.offsetParent.documentOffsetTop() : 0 );
};

jQuery.fn.highlight = function (time) {
    $(this).each(function () {
        var el = $(this);
        $("<div/>")
        .width(50)
        .height(50)
        .css({
            "position": "absolute",
            "left": el.offset().left - 21,
            "top": el.offset().top - 20,
            "background-color": "#ffb3b3",
            "opacity": ".7",
            "z-index": "9999999",
           	"border-radius": "50%"
        }).appendTo('body').fadeOut(time).queue(function () { $(this).remove(); });
    });
}


function scrollToMiddle(el) {
	var top = el.documentOffsetTop() - ( window.innerHeight / 2 );
  window.scrollTo(0, top);
}

// Implements the collapsible blocks the SCP wiki uses
function handleCollapsibleBlocks() {
    var block_links = $("div.collapsible-block a.collapsible-block-link");
          for (var i = 0; i<block_links.length; i++) {
              var item = block_links[i];
              item.addEventListener("click", function() {
              var d = $(this).parents("div.collapsible-block").first();
              if (d.find(".collapsible-block-folded").is(":visible")) {
                  d.find(".collapsible-block-folded").hide();
                  var e = d.find(".collapsible-block-unfolded");
                  e.find(".collapsible-block-content").hide();
                  e.show();
                  e.find(".collapsible-block-content").fadeIn("fast")
              } else {
                  d.find(".collapsible-block-unfolded").hide();
                  d.find(".collapsible-block-folded").show()
              }
              });
          }
}

function handleFootnotes() {
    // Uses the tippy tooltip library to put up tooltips for footnotes
    // Also removes WIKIDOT api calls on the footnoterefs
	function footnoterefs() {
        var footnoterefs = $(".footnoteref a")
        for (var i = 0; i < footnoterefs.length; i++) {
            var footnoteref = footnoterefs[i];
            // Get rid of the WIKIDOT api call to scroll to the footnote
            footnoteref.setAttribute("onclick","");
            var footnoteNumber = footnoteref.id.replace(/^footnoteref\-/, "");

            // Assign a tooltip to every footnoteref corresponding to the footnote at the bottom
            tippy(footnoteref, {
                allowTitleHTML: true,
                hideOnClick: false,
                html: "#footnote-" + footnoteNumber,
                interactive: true,
            });
    }
    // Removes all tooltips on scroll
    window.addEventListener('scroll', () => {
      for (const popper of document.querySelectorAll('.tippy-popper')) {
        const instance = popper._tippy

        if (instance.state.visible) {
          instance.popperInstance.disableEventListeners()
          instance.hide()
        }
      }
    })
  }
  // Implements scrolling up to the footnote when pressed
  // Also does a neat highlight
  // Also removes WIKIDOT api calls
  function footnotes() {
  	var footnotes = $(".footnote-footer a")
    for (var i = 0; i < footnotes.length; i++) {
        var footnote = footnotes[i];
        // Remove WIKIDOT api call
        footnote.setAttribute("onclick","");

      footnote.addEventListener("click", function() {
      var footnoteNumber = $(this).get(0).parentElement.id.replace(/^footnote\-/, "");
      	var footnoteref = $("#footnoteref-"+footnoteNumber);
        var tabview_to_select = footnoteref.closest("div");
        if (tabview_to_select.length != 0 && tabview_to_select.get(0).id.startsWith("wiki-tab")) {
        	var index = parseInt(tabview_to_select.get(0).id.replace("wiki-tab-0-",""));
        	tabview_to_select.parent().prev(".yui-nav").get(0).children.item(index).click();
        }
        var collapsible_block_to_unfold = footnoteref.closest("div.collapsible-block");
        if (collapsible_block_to_unfold.length != 0) {
        	var folded_block = collapsible_block_to_unfold.children(".collapsible-block-folded")
        	var folded = folded_block.is(":visible");
          if (folded)
        		folded_block.children(".collapsible-block-link").get(0).click();
        }
        scrollToMiddle(footnoteref.get(0));
        footnoteref.parent().highlight(2000);
      })
    }
  }

  footnoterefs();
  footnotes();
}
function handleOversizedImages() {
  $(".scp-image-block").css("width","");
  $(".scp-image-block").children("img").css("width","");

  $(".scp-image-block.block-right").children("img").css("width","");
  $(".scp-image-block.block-right").attr("class","scp-image-block block-center")

  $(".scp-image-block.block-left").children("img").css("width","");
  $(".scp-image-block.block-left").attr("class","scp-image-block block-center")

  $(".scp-image-block.block-center").css("width","");
  $(".scp-image-block.block-center").children("img").css("width""");

  $(".scp-image-caption").css("width","");
  $(".image").css("width","");
}
function handleOversizedTables() {
	$(".wiki-content-table").wrap("<div class='table-wrapper'></div>");
}
function handleYUITabView() {
	$(".yui-nav").children("li").click(function() {
  	var element = $(this);
    var index = Array.prototype.indexOf.call(element.parent().get(0).children, $(this).get(0));
  	element.siblings().attr({
    class: "",
    title: ""
    });
    element.attr({
    class: "selected",
    title: "active"
    })
    var yui_content = element.parent().next(".yui-content");
    yui_content.children().css("display","none");
    yui_content.children("#wiki-tab-0-"+index).css("display","block");
  })

}
