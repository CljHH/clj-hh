goog.provide('Label');

goog.require('goog.dom.classes');
goog.require('goog.ui.Component');
goog.require('goog.ui.LabelInput');


Label = function(text, opt_class, opt_domHelper) {
  goog.base(this, opt_domHelper);
  this.text_ = text;
  this.class_ = opt_class;
};
goog.inherits(Label, goog.ui.Component);

Label.prototype.createDom = function() {
  var element = this.dom_.createDom('div', 'label', this.text_);
  if (goog.isDefAndNotNull(this.class_)) {
    goog.dom.classes.add(element, this.class_);
  }
  this.setElementInternal(element);
};

Label.prototype.setText = function(text) {
  this.text_ = text;
  this.getElement().innerHMTL = text;
};
