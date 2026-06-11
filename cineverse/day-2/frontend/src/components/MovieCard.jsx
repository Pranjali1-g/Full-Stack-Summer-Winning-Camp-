export default function MovieCard({ title, rating, genre }) {
  return (
    <div style={{ border: '1px solid #ccc', padding: '15px', borderRadius: '8px', width: '200px' }}>
      <h3>{title}</h3>
      <p><strong>Genre:</strong> {genre}</p>
      <p><strong>Rating:</strong> ⭐ {rating}</p>
      <button style={{ width: '100%', padding: '5px' }}>Book Tickets</button>
    </div>
  );
}