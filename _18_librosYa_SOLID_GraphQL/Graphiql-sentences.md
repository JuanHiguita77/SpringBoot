# Documentación de la API GraphQL

## Consultas (Queries)

### `getAllBooks`
Obtiene una lista paginada de libros con filtros opcionales.

```graphql
query {
  getAllBooks(
    title: String = ""   # Filtro opcional por título del libro.
    author: String = ""  # Filtro opcional por autor.
    genre: String = ""   # Filtro opcional por género.
    page: Int = 1        # Número de página (por defecto: 1).
    size: Int = 5        # Tamaño de la página (por defecto: 5).
  ) {
    content {            # Lista de libros encontrados.
      id                 # ID único del libro.
      title              # Título del libro.
      author             # Autor del libro.
      genre              # Género del libro.
    }
    totalElements        # Total de libros encontrados.
    totalPages           # Total de páginas disponibles.
  }
}

query {
  findBookById(book_id: ID) {
    id          # ID único del libro.
    title       # Título del libro.
    author      # Autor del libro.
    genre       # Género del libro.
  }
}

mutation {
  createBook(
    book: {
      title: String       # Título del libro (obligatorio).
      author: String      # Autor del libro (obligatorio).
      genre: String       # Género del libro (opcional).
      isbn: Int        # Codigo (obligatorio)
      publicationYear: Int     # Año publicacion (Obligatorio)
    }
  ) {
    id            # ID único generado para el libro.
    title         # Título del libro creado.
    author        # Autor del libro creado.
    genre         # Género del libro creado.
    isbn          # Codigo (obligatorio)
    publicationYear   # Año publicacion (Obligatorio)
  }
}

mutation {
  updateBook(
    book_id: ID           # ID del libro a actualizar.
    book: {
      title: String       # Nuevo título del libro (opcional).
      author: String      # Nuevo autor del libro (opcional).
      genre: String       # Nuevo género del libro (opcional).
      isbn: Int        # Codigo (obligatorio)
      publicationYear: Int     # Año publicacion (Obligatorio)
    }
  ) {
    id            # ID único del libro actualizado.
    title         # Nuevo título.
    author        # Nuevo autor.
    genre         # Nuevo género.
    isbn  # Nuevo Codigo (obligatorio)
    publicationYear   # Nuevo Año publicacion (Obligatorio)
  }
}

mutation {
  deleteBook(book_id: ID)      # ID del libro a eliminar.
}
```