//package xyz.dt.dtserver.persistence;
//
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class DeliveryDao {
//
//    private NamedParameterJdbcTemplate jdbc;
//    private static final String COUNT_BOOK = "SELECT COUNT(*) FROM book";
//    private static final String SELECT_BY_ID = "SELECT id, title, author, pages FROM book WHERE id=:id";
//    private static final String DELETE_BY_ID = "DELETE FROM book WHERE id=:id";
//    private static final String UPDATE =
//            "UPDATE book SET\n" +
//                    "title=:title," +
//                    "author=:author," +
//                    "pages=:pages\n" +
//                    "WHERE id=:id";
//
//    // thread 안전하므로 멤버 변수로 선언한뒤 바로 초기화해도 무방
////    RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class);
//    private SimpleJdbcInsert insertAction;
//
//    public BookDao(DataSource dataSource) {
//        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
//        this.insertAction = new SimpleJdbcInsert(dataSource)
//                .withTableName("book")
//                .usingGeneratedKeyColumns("id");
//    }
//
//    public int countBooks() {
//        Map<String, Object> params = Collections.emptyMap();
//        return jdbc.queryForObject(COUNT_BOOK, params, Integer.class);
//    }
//
//    public int insert(Book book){
//        SqlParameterSource params = new BeanPropertySqlParameterSource(book);
//        return insertAction.executeAndReturnKey(params).intValue();
//    }
//
//    public Book selectById(Integer id) {
//
//        // lambda
////        RowMapper<Book> rowMapper = (rs, i) -> {
////            Book book = new Book();
////            book.setId(rs.getInt("id"));
////            book.setTitle(rs.getString("title"));
////            book.setAuthor(rs.getString("author"));
////            book.setPages((Integer) rs.getObject("pages"));
////            return book;
////        };
//
//        // 익명함수
////        RowMapper<Book> rowMapper = new RowMapper<Book>() {
////            @Override
////            public Book mapRow(ResultSet rs, int i) throws SQLException {
////                Book book = new Book();
////                book.setId(rs.getInt("id"));
////                book.setTitle(rs.getString("title"));
////                book.setAuthor(rs.getString("author"));
////                book.setPages((Integer) rs.getObject("pages"));
////                return book;
////            }
////        };
//
//        RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class);
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", id);
//        return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
//    }
//
//    public int deleteById(Integer id){
//        Map<String, ?> params = Collections.singletonMap("id", id);
//        return jdbc.update(DELETE_BY_ID, params);
//    }
//
//    public int update(Book book) {
//        SqlParameterSource params = new BeanPropertySqlParameterSource(book);
//        return jdbc.update(UPDATE, params);
//    }
//}
//}
