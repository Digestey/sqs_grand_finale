import { SharedArray } from 'k6/data';
// not using SharedArray here will mean that the code in the function call (that is what loads and
// parses the json) will be executed per each VU which also means that there will be a complete copy
// per each VU
const books = new SharedArray('available books', function () {
    return JSON.parse(open('/home/k6/data/books.json')).books;
});

const stores = new SharedArray('available stores', function () {
    return JSON.parse(open('/home/k6/data/stores.json')).stores;
});

export function getRandomBook() {
    return books[rand(books.length - 1)]
}

export function getRandomStore() {
    return stores[rand(stores.length - 1)]
}

function rand(to){
    return Math.round(Math.random() * to);
}