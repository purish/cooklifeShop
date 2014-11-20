
//定义CKEditor对象引用
var editor = null;

//初始化CKEditor
function initCKEditor(textObjId, rootPath){
	editor = CKEDITOR.replace(textObjId , {
		uiColor : '#DDDDDD',
		language : 'zh-cn',
		height : '300px',
		on : {
			instanceReady : function(ev){
				this.dataProcessor.writer.setRules('p' , {
					indent : false,
					breakBeforeOpen : false,
					breakAfterOpen : false,
					breakBeforeClose : false,
					breakAfterClose : false
				});
			}
		}
	});

	CKFinder.setupCKEditor(editor, rootPath + '/admin/js/ckfinder/');

	CKEDITOR.editorConfig = function(config){
		filebrowserBrowseUrl = rootPath + '/admin/js/ckfinder/ckfinder.html';
		filebrowserImageBrowseUrl = rootPath + '/admin/js/ckfinder/ckfinder.html?type=Images';
		filebrowserFlaseBrowseUrl = rootPath + '/admin/js/ckfinder/ckfinder.html?type=Flash';
		filebrowserUploadUrl = rootPath + '/admin/js/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
		filebrowserFlashUploadUrl = rootPath + '/admin/js/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';
	}
}