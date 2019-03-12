(function(Handosntable){
	var My97DateRenderer = function (instance, TD, row, col, prop, value, cellProperties) {
	  Handsontable.renderers.TextRenderer.apply(this, arguments);
	};
	
	Handosntable.My97DateRenderer = My97DateRenderer;
	Handosntable.renderers.My97DateRenderer = My97DateRenderer;
	Handosntable.renderers.registerRenderer('my97date', My97DateRenderer);
})(Handsontable);

Handsontable.My97DateCell = {
		  editor: Handsontable.editors.My97DateEditor,
		  renderer: Handsontable.renderers.My97DateRenderer,
		  copyable: false
};
Handsontable.cellTypes = {my97date:Handsontable.My97DateCell}