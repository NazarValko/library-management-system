const booksDropdown = document.getElementById('books-dropdown');
const subMenu = document.querySelector('.books-sub-menu');

// Додаємо обробник події при кліку на пункт меню "Books"
booksDropdown.addEventListener('click', function(event) {
    event.preventDefault(); // Зупиняємо перехід за посиланням
    if (subMenu.style.display === 'block') {
        subMenu.style.display = 'none';
    } else {
        subMenu.style.display = 'block';
    }
});

const userDropdown = document.getElementById('user-dropdown');
const subMenuUser = document.querySelector('.user-sub-menu');

// Додаємо обробник події при кліку на пункт меню "Books"
userDropdown.addEventListener('click', function(event) {
    event.preventDefault(); // Зупиняємо перехід за посиланням
    if (subMenuUser.style.display === 'block') {
        subMenuUser.style.display = 'none';
    } else {
        subMenuUser.style.display = 'block';
    }
});