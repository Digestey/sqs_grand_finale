import http from 'k6/http';
import { sleep } from 'k6';


import {BASE_URL} from "../internals/config.js";
import {getRandomStore, getRandomBook} from "../internals/data.js";

export const options = {};

export default function () {
    const storeID = getRandomStore()
    const book = getRandomBook()

    //HTTP Requests: https://k6.io/docs/using-k6/http-requests/

    //Book Body
    // `[
    //   {
    //     "isbn": "${book.isbn}",
    //     "title": "${book.title}",
    //     "authors": ${JSON.stringify(book.authors)}
    //   }
    // ]`

    // get all books
    // find all books in store
    // find all books with isbn
    // add book to store
    // find book in store
    // delete book from store
}
