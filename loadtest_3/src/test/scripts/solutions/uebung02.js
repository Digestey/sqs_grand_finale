import http from 'k6/http';
import { check } from 'k6';


import {BASE_URL} from "../internals/config.js";
import {getRandomStore, getRandomBook} from "../internals/data.js";

export const options = {
    stages: [
        { duration: '10s', target: 100 }, // below normal load
        { duration: '30s', target: 100 },
        { duration: '10s', target: 200 }, // normal load
        { duration: '30s', target: 200 },
        { duration: '10s', target: 500 }, // around the breaking point
        { duration: '10s', target: 500 },
        { duration: '10s', target: 1000 }, // beyond the breaking point
        { duration: '30s', target: 1000 },
        { duration: '15s', target: 0 }, // scale down. Recovery stage.
    ],
};

const params = {
    headers: {
        'Content-Type': 'application/json',
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
    const res = http.post(
        `${BASE_URL}/api/order?store-id=${storeID}`,
        `[
          {
            "isbn": "${book.isbn}",
            "title": "${book.title}",
            "authors": ${JSON.stringify(book.authors)}
          }
        ]`,
        params
    );
    check(res, {'status was 201' : r => r.status === 201})

    // find book in store
    http.get(`${BASE_URL}/api/books/${book.isbn}`, params);

    // delete book from store manually

}