// Figures out if element is hidden
function isHidden(el) {
    return (el.style.display === "none")
}
// Hides an element
function hide(el) {
	el.style.display = "none";
}
// Shows an element
function show(el) {
	el.style.display = "block";
}

// Called when page is loaded
function onLoad() {
    handleCollapsibleBlocks();
}

// Fades in an element
// Stolen off of stack overflow ¯\_(ツ)_/¯ (https://stackoverflow.com/a/6121270)
function fadeIn(element) {
    var op = 0.1;  // initial opacity
    element.style.display = 'block';
    var timer = setInterval(function () {
        if (op >= 1){
            clearInterval(timer);
        }
        element.style.opacity = op;
        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
        op += op * 0.1;
    }, 10);
}

// Implements the collapsible blocks the SCP wiki uses
function handleCollapsibleBlocks() {
    // Get all (soon-to-be) clickable block links
    var collapsible_block_links = document.getElementsByClassName("collapsible-block-link");
        for (var i = 0; i < collapsible_block_links.length; i++) {
            var element = collapsible_block_links[i];
            // Add the click functionality
            // Closes if open
            // Opens if closes
            element.addEventListener("click", function() {
                var collapsible_blocks = element.parentNode.parentNode.parentNode.children
                var folded = collapsible_blocks[0];
                var unfolded = collapsible_blocks[1];
                if (!isHidden(folded)) {
                    hide(folded);
                    hide(unfolded.children[1]);
                    show(unfolded);
                    fadeIn(unfolded.children[1]);
                }
                else {
                    hide(unfolded);
                    show(folded);
                }
            });
        }
}