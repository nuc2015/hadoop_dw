(function(Handsontable){

  var My97DateEditor = Handsontable.editors.TextEditor.prototype.extend();

  My97DateEditor.prototype.createElements = function () {
    Handsontable.editors.TextEditor.prototype.createElements.apply(this, arguments);
    alert("ukkk..");
    this.TEXTAREA = document.createElement('input');
    this.TEXTAREA.setAttribute('type', 'text');
    this.TEXTAREA.setAttribute('readOnly', 'true');
    this.TEXTAREA.className = 'Wdate';
    this.textareaStyle = this.TEXTAREA.style;
    this.textareaStyle.width = 0;
    this.textareaStyle.height = 0;
    var that=this;
    var eventManager = Handsontable.eventManager(this);
     eventManager.addEventListener(this.TEXTAREA, 'focus', function (event) {
      WdatePicker(that.cellProperties.data?that.cellProperties.data:{"dateFmt":"yyyy-MM-dd"});
    });
    Handsontable.Dom.empty(this.TEXTAREA_PARENT);
    this.TEXTAREA_PARENT.appendChild(this.TEXTAREA);
  };
  Handsontable.editors.My97DateEditor = My97DateEditor;
  Handsontable.editors.registerEditor('my97date', My97DateEditor);

})(Handsontable);
