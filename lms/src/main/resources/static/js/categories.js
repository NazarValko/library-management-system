// document.addEventListener("DOMContentLoaded", function() {
//     const editButtons = document.querySelectorAll(".edit-button");
//     const overlay = document.getElementById("overlay");
//     const editForm = document.getElementById("edit-form");
//
//     editButtons.forEach(function(button) {
//         button.addEventListener("click", function() {
//             overlay.style.display = "block";
//             editForm.style.display = "block";
//         });
//     });
//
//     overlay.addEventListener("click", function() {
//         overlay.style.display = "none";
//         editForm.style.display = "none";
//     });
// });

document.addEventListener("DOMContentLoaded", function () {
    var editButtons = document.querySelectorAll(".edit-button");
    var overlay = document.getElementById("overlay");
    var editForm = document.getElementById("edit-form");

    editButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            var categoryId = this.getAttribute("data-category-id");
            overlay.style.display = "block";
            editForm.style.display = "block";
        });
    });

    overlay.addEventListener("click", function () {
        overlay.style.display = "none";
        editForm.style.display = "none";
    });
});







