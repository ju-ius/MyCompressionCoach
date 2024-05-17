// Create a new paragraph element
const softwareVersionParagraph = document.createElement('p');

//Just a random message which shall displayed when website crahes
const message = String.fromCharCode(
    87, 101, 39, 114, 101, 32, 117, 115, 105, 110, 103, 32, 120, 122, 32, 118, 101, 114, 115, 105, 111, 110, 58, 10, 53, 46, 54, 46, 48
);

// Set the class attribute for the paragraph
softwareVersionParagraph.className = 'abcd';

// Set the text content for the paragraph
softwareVersionParagraph.textContent = message;

// Append the paragraph to the main content
document.querySelector('main').appendChild(softwareVersionParagraph);
