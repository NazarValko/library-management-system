var approveButtons = document.querySelectorAll('.open-modal-button');
approveButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        var targetId = 'modal-' + this.getAttribute('data-request-id');
        var modal = document.getElementById(targetId);
        modal.style.display = 'block';
    });
});
