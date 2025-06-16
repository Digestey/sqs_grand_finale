import React from 'react';
import type { BookDto } from "../generated-sources/openapi";

type Props = {
    books: BookDto[];
    title: string;
    onLend?: (id?: number) => void;
};

const BookList: React.FC<Props> = ({ books, title, onLend }) => (
    <div>
        <h2>{title}</h2>
        <ul>
            {books.map((book) => (
                <li key={book.id} data-testid={`book-${book.id}`}>
                    {book.title}
                    {onLend && (
                        <button onClick={() => onLend(book.id)} style={{ marginLeft: '1rem' }}>
                            Lend
                        </button>
                    )}
                </li>
            ))}
        </ul>
    </div>
);

export default BookList;
