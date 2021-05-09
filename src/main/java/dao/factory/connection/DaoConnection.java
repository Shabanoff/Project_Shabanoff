package dao.factory.connection;

public interface DaoConnection extends AutoCloseable{

    /**
     * Starts transaction with level {@code TRANSACTION_REPEATABLE_READ}
     */
    void startTransaction();

    /**
     * Starts transaction with level {@code TRANSACTION_SERIALIZABLE}
     */
    void startSerializableTransaction();

    /**
     * Commits transaction.
     */
    void commit();

    /**
     * Rollback transaction
     */
    void rollback();

    /**
     * @return database specific connection
     */
    Object getNativeConnection();

    @Override
    void close();

}