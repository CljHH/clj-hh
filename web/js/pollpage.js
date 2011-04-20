goog.provide('PollPage');

goog.require('goog.ui.Component');


PollPage = function(opt_domHelper) {
    goog.base(this, opt_domHelper);
};
goog.inherits(PollPage, goog.ui.Component);

PollPage.prototype.createDom = function() {
    var element = this.dom_.createDom('div', 'poll-page', 'Poll Page');
    this.setElementInternal(element);
}