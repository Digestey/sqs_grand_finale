import { useEffect, useState } from 'react';
import { type BookDto } from "./generated-sources/openapi";
import BookList from "./components/BookListComponent.tsx";
import BooksApiService from './api/bookApi.ts';
import './App.css';

function App() {
    const [availableBooks, setAvailableBooks] = useState<BookDto[]>([]);
    const [lentBooks, setLentBooks] = useState<BookDto[]>([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState<BookDto[]>([]);

    const handleSearch = async () => {
        if (searchQuery.trim()) {
            const results = await BooksApiService.searchBookByTitle(searchQuery);
            setSearchResults(results);
        }
    };

    const fetchBooks = async () => {
        const [available, lent] = await Promise.all([
            BooksApiService.getAvailableBooks(),
            BooksApiService.getLentBooks()
        ]);
        setAvailableBooks(available);
        setLentBooks(lent);
    };

    const handleLend = async (id?: number) => {
        try {
            if (id !== undefined) {
                await BooksApiService.lendBook(id);
                await fetchBooks();
            }
        } catch (e) {
            alert('Error lending book. Book may already be lent or does not exist.');
        }
    };

    useEffect(() => {
        fetchBooks();
    }, []);

    return (
        <div className="app-container">
            <h1 className="title">📚 Library Dashboard</h1>

            {/* Search Input */}
            <div style={{ marginBottom: '2rem', display: 'flex', gap: '1rem', alignItems: 'center' }}>
                <input
                    type="text"
                    value={searchQuery}
                    placeholder="Search books by title..."
                    onChange={(e) => setSearchQuery(e.target.value)}
                    style={{
                        flex: 1,
                        padding: '0.5rem 1rem',
                        fontSize: '1rem',
                        borderRadius: '6px',
                        border: '1px solid #ccc'
                    }}
                />
                <button
                    onClick={handleSearch}
                    style={{
                        padding: '0.5rem 1.25rem',
                        backgroundColor: '#007bff',
                        color: '#fff',
                        border: 'none',
                        borderRadius: '6px',
                        cursor: 'pointer'
                    }}
                >
                    Search
                </button>
            </div>

            {/* Book Columns */}
            <div className="book-columns">
                <div className="book-column">
                    <BookList books={availableBooks} title="Available Books" onLend={handleLend} />
                </div>
                <div className="book-column">
                    <BookList books={lentBooks} title="Lent Books" />
                </div>
                {searchResults.length > 0 && (
                    <div className="book-column" data-testid="search-results">
                        <BookList books={searchResults} title={`Search Results for "${searchQuery}"`} />
                    </div>
                )}
            </div>
        </div>
    );


}

export default App;
