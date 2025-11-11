(function ($, window) {
    const UiMode = {
        ENRICHED: 'enriched',
        PLAIN: 'plain'
    };
    let uiMode = UiMode.ENRICHED;
    const uiModeStorageKey = 'uiMode';

    function initInlineEditable() {
        // Bootstrap-editable for Bootstrap 3 is incompatible with Bootstrap 5
        // Disabled until a Bootstrap 5 compatible solution is found
        // $('.inline-editable').editable({mode: 'inline'});
        console.warn('Inline editing disabled: bootstrap-editable is not compatible with Bootstrap 5');
    }

    function uiStandardHtml() {
        // Bootstrap-editable for Bootstrap 3 is incompatible with Bootstrap 5
        // try {
        //     $('.inline-editable').editable('destroy');
        // } catch (e) {
        //     // editable plugin not available
        // }
        $('.href-link')
            .removeAttr('role')
            .removeAttr('data-bs-toggle')
            .removeAttr('data-bs-target')
            .removeAttr('data-bs-remote')
            .removeClass('btn btn-secondary');
    }

    function uiEnriched() {
        initInlineEditable();

        /**
         * Change the links into model triggers but don't let Bootstrap do its magic loading the content into the
         * modal-content div because it messes up the UI.
         */
        $('.href-link')
            .attr('role', 'button')
            .attr('data-bs-toggle', 'modal')
            .attr('data-bs-target', '#uiModal')
            .attr('data-bs-remote', 'false')
            .addClass('btn btn-secondary');
    }

    function toggleUiMode() {
        const _uiMode = getUiMode();
        if (_uiMode && _uiMode === UiMode.ENRICHED) {
            uiStandardHtml();
            setUiMode(UiMode.PLAIN)
        } else {
            uiEnriched();
            setUiMode(UiMode.ENRICHED);
        }
    }

    function addButtonToToggleUi() {
        const $uiToggle = $('#ui-toggle');
        if ($uiToggle.length === 0) {
            return; // Element doesn't exist, skip
        }
        const $toggleUiBtn = $('<a></a>')
            .attr('role', 'button')
            .text('Toggle')
            .on('click', toggleUiMode);
            
        $uiToggle
            .html($toggleUiBtn)
            .removeClass('hidden');
    }

    function supportsSessionStorage() {
        if (window.sessionStorage === undefined) {
            return false;
        }

        try {
            window.sessionStorage.setItem('test', '');
            window.sessionStorage.removeItem('test');
            return true;
        } catch (e) {
            return false;
        }
    }

    function getUiMode() {
        if (supportsSessionStorage()) {
            const storedMode = window.sessionStorage.getItem(uiModeStorageKey) || '';
            if (storedMode.length > 0) {
                uiMode = storedMode;
            }
        }
        return uiMode;
    }

    function setUiMode(_uiMode) {
        if (supportsSessionStorage()) {
            window.sessionStorage.setItem(uiModeStorageKey, _uiMode);
        } else {
            uiMode = _uiMode;
        }
    }

    function initUi() {
        const preferredUiMode = getUiMode();
        if (preferredUiMode && preferredUiMode === UiMode.ENRICHED) {
            uiEnriched();
        }
        addButtonToToggleUi();
    }

    initUi();
})(jQuery, window);