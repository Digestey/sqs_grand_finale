import http from 'k6/http';
import {getRandomBook, getRandomStore} from "../internals/data";
import {BASE_URL} from "../internals/config";

export const options = {
    scenarios: {
        closed_model: {
            executor: 'constant-vus',
            vus: 100,
            duration: '1m',
        },
    },
};


export default function () {
    const storeID = getRandomStore()
    const book = getRandomBook()

    // get all books
    http.get(`${BASE_URL}/api/books`, params);
    // find all books in store
    http.get(`${BASE_URL}/api/books?store-id=${storeID}`, params);
    // find all books with isbn
    http.get(`${BASE_URL}/api/books/${book.isbn}`, params);
    // add book to store
    http.post(
        `${BASE_URL}/api/order?store-id=${storeID}`,
        `[
          {
            "isbn": "${book.isbn}",
            "title": "${book.title}",
            "authors": ["${book.author}"]
          }
        ]`,
        params
    );
    // find book in store
    http.get(`${BASE_URL}/api/books/${book.isbn}`, params);

    // delete book from store manually

}
