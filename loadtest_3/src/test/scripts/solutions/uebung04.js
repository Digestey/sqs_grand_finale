import http from 'k6/http';


import {BASE_URL} from "../internals/config.js";
import {getRandomStore, getRandomBook} from "../internals/data.js";

export const options = {
    stages: [
        { duration: '10s', target: 100 }, // below normal load
        { duration: '30s', target: 100 },
        { duration: '10s', target: 200 }, // normal load
        { duration: '30s', target: 200 },
        { duration: '10s', target: 300 }, // around the breaking point
        { duration: '10s', target: 300 },
        { duration: '10s', target: 1000 }, // beyond the breaking point
        { duration: '30s', target: 1000 },
        { duration: '15s', target: 0 }, // scale down. Recovery stage.
    ],
    thresholds: {
        http_req_duration: ['p(95)<200'], // 95% of requests should be below 200ms
    },
};

export default function () {
    const storeID = getRandomStore()
    const book = getRandomBook()

    // get all books
    http.get(`${BASE_URL}/api/books`);
    // find all books in store
    http.get(`${BASE_URL}/api/books?store-id=${storeID}`);
    // find all books with isbn
    http.get(`${BASE_URL}/api/books/${book.isbn}`);
    // add book to store
    http.post(
        `${BASE_URL}/api/order?store-id=${storeID}`,
        `[
          {
            "isbn": "${book.isbn}",
            "title": "${book.title}",
            "author": "${book.author}"
          }
        ]`
    );
    // find book in store
    http.get(`${BASE_URL}/api/books/${book.isbn}`);

    // delete book from store
    http.del(`${BASE_URL}/api/books/${book.isbn}`);

}
