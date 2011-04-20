goog.provide('Poll');

goog.require('PollPage');

Poll.init = function() {
    new PollPage().render();
};
