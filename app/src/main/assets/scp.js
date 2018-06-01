// Called when page is loaded
function onLoad() {
    handleCollapsibleBlocks();
    handleFootnotes();
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
// Uses the tippy tooltip library to put up tooltips for footnotes
function handleFootnotes() {
    var footnotes = $(".footnoteref a")
    for (var i = 0; i < footnotes.length; i++) {
        var footnote = footnotes[i];
        // Get rid of the WIKIDOT api call to scroll to the footnote
        footnote.setAttribute("onclick","");
        var footnoteNumber = footnote.id.replace(/^footnoteref\-/, "");
        // Gets the footnote at the bottom that corresponds to the footnote ref
        var footnoteBottom = $("#footnote-" + footnoteNumber).get(0);

        // Set the title attribute to the bottom footnotes html
        // Tippy looks at the title attribute to decide what to display
        footnote.setAttribute("title",footnoteBottom.innerHTML);
        tippy(footnote);
    }
}