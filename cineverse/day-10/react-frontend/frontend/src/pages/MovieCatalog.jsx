import Navbar from '../components/Navbar';
import MovieCard from '../components/MovieCard';

// Day 02 Static Data Collection Simulation
const mockMovies = [
  { id: 1, title: "Inception", rating: "8.8", genre: "Sci-Fi" },
  { id: 2, title: "The Dark Knight", rating: "9.0", genre: "Action" },
  { id: 3, title: "Interstellar", rating: "8.6", genre: "Sci-Fi" },
  { id: 4, title: "Parasite", rating: "8.5", genre: "Thriller" }
];

export default function MovieCatalog() {
  return (
    <div>
      <Navbar />
      <div style={{ padding: '20px' }}>
        <h2>Trending Movie Catalog</h2>
        <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap', marginTop: '20px' }}>
          {mockMovies.map(movie => (
            <MovieCard 
              key={movie.id} 
              title={movie.title} 
              rating={movie.rating} 
              genre={movie.genre} 
            />
          ))}
        </div>
      </div>
    </div>
  );
}