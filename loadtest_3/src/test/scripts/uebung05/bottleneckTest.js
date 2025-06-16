import http from 'k6/http';
import { check } from 'k6';


import {BASE_URL} from "../internals/config.js";
import {getRandomStore, getRandomBook} from "../internals/data.js";

export const options = {
    stages: [
        {duration: '20s', target: 100}, // below normal load
        {duration: '30s', target: 100},
        {duration: '20s', target: 200}, // normal load
        {duration: '30s', target: 200},
        {duration: '20s', target: 500}, // around the breaking point
        {duration: '30s', target: 500},
        {duration: '20s', target: 1000}, // beyond the breaking point
        {duration: '30s', target: 1000},
        {duration: '15s', target: 100}, // scale down. Recovery stage.
        {duration: '30s', target: 100},
        {duration: '15s', target: 0},
    ],
};

export default function () {

    const storeID = getRandomStore()
    const book = getRandomBook()

    // get all books
    http.get(
        `${BASE_URL}/api/books`,
        {
            headers: {'Content-Type': 'application/json'},
            tags: {name: 'allBooks'}
        });

    // find all books in store
    http.get(
        `${BASE_URL}/api/books?store-id=${storeID}`,
        {
            headers: {'Content-Type': 'application/json'},
            tags: {name: 'bookByStore'}
        }
    );

    // find all books with isbn
    http.get(
        `${BASE_URL}/api/books/${book.isbn}`,
        {
            headers: {'Content-Type': 'application/json'},
            tags: {name: 'bookByIsbn'}
        }
    );

    // add book to store
    const res = http.post(
        `${BASE_URL}/api/order?store-id=${storeID}`,
        `[
          {
            "isbn": "${book.isbn}",
            "title": "${book.title}",
            "authors": ${JSON.stringify(book.authors)}
          }
        ]`,
        {
            headers: {'Content-Type': 'application/json'},
            tags: {name: 'orderBook'}
        }
    );
    check(res, {'status was 201': r => r.status === 201})

    // find book in store
    http.get(
        `${BASE_URL}/api/books/${book.isbn}`,
        {
            headers: {'Content-Type': 'application/json'},
            tags: {name: 'bookByIsbn'}
        });

}