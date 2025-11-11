(function ($) {
    const initModalEventHandlers = () => {
        // loads content into .modal-body when the modal is being shown
        const $uiModal = $('#uiModal');
        if ($uiModal.length === 0) {
            return; // Modal doesn't exist, skip initialization
        }
        $uiModal.on('show.bs.modal', (e) => {
            const $modal = $(e.target);
            const trigger = e.relatedTarget;
            // Check if trigger exists to avoid null pointer errors
            if (!trigger) {
                return;
            }
            const location = trigger.getAttribute('href');
            if (location) {
                $modal.find('.modal-title').text(trigger.innerText || '');
                // Load content with error handling
                $modal.find('.modal-body').load(location, function(response, status, xhr) {
                    if (status === "error") {
                        $(this).html('<div class="alert alert-danger">Failed to load content. Please try again.</div>');
                    }
                });
            }
        });

        // Closes the modal when the user clicks the cancel button instead of following its link
        $('.modal').on('click', '.btn-cancel', (e) => {
            e.preventDefault();
            $(e.delegateTarget).modal('hide');
        });

        // Make modals draggable
        $('.modal-dialog').draggable({
            handle: '.modal-header'
        });
    }

    initModalEventHandlers();
})(jQuery);