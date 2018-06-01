// Called when page is loaded
function onLoad() {
    handleCollapsibleBlocks();
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
