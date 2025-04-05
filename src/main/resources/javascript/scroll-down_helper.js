// ScrollDown.js
var ScrollUtils = {
    // Scrolls the window down by a specified number of pixels
    scrollDownBy: function(pixels) {
        window.scrollBy(0, pixels);
    },

    // Scrolls to the bottom of the page
    scrollToBottom: function() {
        window.scrollTo(0, document.body.scrollHeight);
    },

    // Scrolls to a specific element on the page
    scrollToElement: function(selector) {
        var element = document.querySelector(selector);
        if (element) {
            element.scrollIntoView();
        }
    }
};
