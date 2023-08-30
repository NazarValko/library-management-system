var closeButtons = document.querySelectorAll('.close-modal-button');
closeButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        var modal = this.closest('.modal');
        modal.style.display = 'none';
    });
});