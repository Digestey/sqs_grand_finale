import http from 'k6/http';
import {getRandomBook, getRandomStore} from "../internals/data";
import {BASE_URL} from "../internals/config";

export const options = {
    scenarios: {
        open_model: {
            executor: 'constant-arrival-rate',
            // Alternative ramping-arrival-rate, falls Stages eingefügt werden sollen: https://k6.io/docs/using-k6/scenarios/executors/ramping-arrival-rate/
            rate: 100,
            timeUnit: '1s',
            duration: '1m',
            preAllocatedVUs: 20,
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
