goog.provide('PollDesigner');

goog.require('Label');

goog.require('goog.events.EventType');
goog.require('goog.ui.Component');
goog.require('goog.ui.Dialog');

PollDesigner = function(opt_domHelper) {
  goog.base(this, opt_domHelper);
};
goog.inherits(PollDesigner, goog.ui.Dialog);

PollDesigner.prototype.createDom = function() {
  goog.base(this, 'createDom');
  this.content_ = this.getContentElement();
  goog.dom.classes.add(this.content_, 'poll-designer');

  this.title_ = new goog.ui.LabelInput('Please enter a title ...');
  this.addChild(this.title_);
  this.title_.render(this.content_);
  goog.dom.classes.add(this.title_.getElement(), 'title');

  this.options_ = new Label('', 'options');
  this.addChild(this.options_);
  this.options_.render(this.content_);

  this.optionName_ = new goog.ui.LabelInput('Option');
  this.addChild(this.optionName_);
  this.optionName_.render(this.content_);
  goog.dom.classes.add(this.optionName_.getElement(), 'option-name');

  this.addOption_ = new Label('Add', 'add-option');
  this.addChild(this.addOption_);
  this.addOption_.render(this.content_);
};

PollDesigner.prototype.enterDocument = function() {
  goog.base(this, 'enterDocument');
  this.getHandler().listen(this.addOption_.getElement(),
                           goog.events.EventType.CLICK,
                           this.onAddOption_,
                           false,
                           this);
    this.setTitle('Create a new poll');
  this.setVisible(true);
};

PollDesigner.prototype.onAddOption_ = function(event) {
  var name = this.optionName_.getValue();
  var option = new Label(name, 'option');
  this.addChild(option);
  option.render(this.options_.getElement());
  this.optionName_.setValue('');
};
