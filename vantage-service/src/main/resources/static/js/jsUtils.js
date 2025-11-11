(function ($, window) {

    const StorageType = {
        LOCAL: 'localStorage',
        SESSION: 'sessionStorage'
    };

    let storageType = StorageType.SESSION;
    const localStorageKey = 'localStorageKey';

    /**
     * Adds the data to the active local storage type.
     * @param key
     * @param value
     */
    function addKey(key, value) {
        if (!window.localStorage || !window.JSON || !key) {
            return;
        }

        const _storageType = getStorageType();
        if (_storageType && StorageType.SESSION === _storageType) {
            // writes to session storage
            sessionStorage.setItem(key, value);
        } else {
            // writes to local storage
            localStorage.setItem(key, value);
        }
    };

    /**
     * Returns the value of the <code>key</code>.
     *
     * @param key
     * @returns {any}
     */
    function getValue(key) {
        if (!window.localStorage || !window.JSON || !key) {
            return;
        }

        var value = null;
        const _storageType = getStorageType();
        if (_storageType && StorageType.SESSION === _storageType) {
            // reads to session storage
            value = sessionStorage.getItem(key);
        } else {
            // reads to local storage
            value = localStorage.getItem(key);
        }

        return value;
    };

    /**
     * Removes the data from the client's storage.
     * @param key
     */
    function removeKey(key) {
        if (!window.localStorage || !window.JSON || !key) {
            return;
        }

        const _storageType = getStorageType();
        if (_storageType && StorageType.SESSION === _storageType) {
            // removes from session storage
            sessionStorage.removeItem(key);
        } else {
            // removes from local storage
            localStorage.removeItem(key);
        }
    }

    /**
     *
     * @param key
     * @param data
     */
    function addData(key, data) {
        if (!window.localStorage || !window.JSON || !key) {
            return;
        }

        addKey(key, JSON.stringify(data));
    };

    /**
     *
     * @param key
     * @returns {any}
     */
    function getData(key) {
        if (!window.localStorage || !window.JSON || !key) {
            return;
        }

        var data = getValue(key);
        if (!data) {
            return;
        }

        return JSON.parse(data);
    };

    // function initInlineEditable() {
    //     $('.inline-editable').editable({mode: 'inline'});
    // }
    //
    // function uiStandardHtml() {
    //     $('.inline-editable').editable('destroy');
    //     $('.href-link')
    //         .removeAttr('role')
    //         .removeAttr('data-toggle')
    //         .removeAttr('data-target')
    //         .removeAttr('data-remote')
    //         .removeClass('btn btn-default');
    // }
    //
    // function uiEnriched() {
    //     initInlineEditable();
    //
    //     /**
    //      * Change the links into model triggers but don't let Bootstrap do its magic loading the content into the
    //      * modal-content div because it messes up the UI.
    //      */
    //     $('.href-link')
    //         .attr('role', 'button')
    //         .attr('data-toggle', 'modal')
    //         .attr('data-target', '#uiModal')
    //         .attr('data-remote', 'false')
    //         .addClass('btn btn-default');
    // }

    /**
     * Button toggles the session storage type.
     */
    function toggleStorage() {
        const _storageType = getStorageType();
        if (_storageType && StorageType.SESSION === _storageType) {
            // uiStandardHtml();
            setStorageType(StorageType.LOCAL);
        } else {
            // uiEnriched();
            setStorageType(StorageType.SESSION);
        }
    }

    /**
     * Adds the <code>toggleStorageUiButton</code>.
     */
    function addToggleStorageUiButton() {
        const $uiToggleStorage = $('#ui-toggle-storage');
        if ($uiToggleStorage.length === 0) {
            return; // Element doesn't exist, skip
        }
        const $toggleStorageUiButton = $('<a></a>')
            .attr('role', 'button')
            .text('Storage')
            .on('click', toggleStorage);
        $uiToggleStorage
            .html($toggleStorageUiButton)
            .removeClass('hidden');
    }

    /**
     * Returns true if the <code>sessionStorage</code> is supported by application otherwise false.
     * @returns {boolean}
     */
    function supportsSessionStorage() {
        if (window.sessionStorage === undefined) {
            return false;
        }

        try {
            window.sessionStorage.setItem('toggleStorage', '');
            window.sessionStorage.removeItem('toggleStorage');
            return true;
        } catch (e) {
            return false;
        }
    }

    /**
     * Returns the <code>storageType</code> used by application.
     * @returns {string}
     */
    function getStorageType() {
        if (supportsSessionStorage()) {
            const _storageType = window.sessionStorage.getItem(localStorageKey) || '';
            if (_storageType && _storageType.length > 0) {
                storageType = _storageType;
            }
        }

        return storageType;
    }

    /**
     * The <code>storageType</code> to be set.
     *
     * @param _storageType
     */
    function setStorageType(_storageType) {
        if (supportsSessionStorage()) {
            window.sessionStorage.setItem(localStorageKey, _storageType);
        } else {
            storageType = _storageType;
        }
    }

    /**
     * Initializes the toggle storage ui.
     */
    function initStorageType() {
        const preferredStorageType = getStorageType();
        // Storage type is initialized, no UI mode check needed here

        addToggleStorageUiButton();
    }

    initStorageType();
})(jQuery, window);