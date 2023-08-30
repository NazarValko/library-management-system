document.addEventListener("DOMContentLoaded", function () {
    var editButtons = document.querySelectorAll(".edit-button");
    var overlay = document.getElementById("overlay");
    var editForm = document.getElementById("add-review-form");

    editButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            var bookId = this.getAttribute("data-book-id");
            overlay.style.display = "block";
            editForm.style.display = "block";
        });
    });

    overlay.addEventListener("click", function () {
        overlay.style.display = "none";
        editForm.style.display = "none";
    });
});