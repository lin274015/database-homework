package databasefinal;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // 更新資料表結構
        SchemaUpdater.updateSchema();

        // 插入示例資料
        DataInserter.insertSampleData();

        // 啟動 Swing UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
}
