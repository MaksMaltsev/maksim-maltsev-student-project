package db.creators;

public class TableFactory implements CreatorFactory{
    @Override
    public MySqlLoader createElement() {
        return new CreateTable();
    }
}
