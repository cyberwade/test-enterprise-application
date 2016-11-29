(function($) {
    $.fn.cleanup = function() {
        this.find('input:text, input:password, input:file, select, textarea')
            .val('')
            .change();
        this.find('input:radio, input:checkbox')
            .removeAttr('checked')
            .removeAttr('selected')
            .change();

        return this;
  };
})(jQuery);
