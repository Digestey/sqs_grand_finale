import { BooksApi, Configuration } from "../generated-sources/openapi";


const booksApi: BooksApi = new BooksApi(new Configuration(
    {
        basePath: ''
    }));

class BooksApiService {

    async getAvailableBooks() {
        return booksApi.available().then(res => res.data);
    }

    async getLentBooks() {
        return booksApi.lent().then(res => res.data);
    }

    async lendBook(bookId: number) {
        await booksApi.lend(bookId);
    }

    async searchBookByTitle(query: string) {
        return booksApi.searchBooks(query).then(res => res.data);
    }

}

export const service = new BooksApiService();
export default service;
