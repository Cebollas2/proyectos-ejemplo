(function() {
  var VAADIN_DIR_URL = typeof CKEDITOR.vaadinDirUrl !== 'undefined'? CKEDITOR.vaadinDirUrl : "../../../";


  CKEDITOR.plugins.add('magnoliaexpand', {
    init: function (editor) {
      editor.ui.addButton('Expand', {
        label: 'Expand',
        command: 'expand',
        icon: "/cione-theme/webresources/img/open-fullscreen.svg"
      });

      editor.addCommand("expand", {
        exec: expandToDialog()
      });

      function expandToDialog() {
        return function(editor) {
          editor.fire('mgnlExpandToDialog');
        }
      }
    }
  });
})();