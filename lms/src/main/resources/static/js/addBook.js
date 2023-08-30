function toggleCategoryInput() {
    const categoryInput = document.getElementById('category');
    const categoryList = document.getElementById('categoryList');

    if (!categoryInput.value || categoryInput.value === '') {
        categoryInput.setAttribute('list', 'categoryList');
    } else {
        categoryInput.removeAttribute('list');
    }
}

function toggleAuthorInput() {
    const authorInput = document.getElementById('author');
    const authorList = document.getElementById('authorList');

    if (!authorInput.value || authorInput.value === '') {
        authorInput.setAttribute('list', 'authorList');
    } else {
        authorInput.removeAttribute('list');
    }
}