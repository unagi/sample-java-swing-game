package app.spec;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.launcher.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SpecMarkdownListener implements TestExecutionListener {
    private TestPlan plan;

    private static class Entry {
        final String name;
        final TestExecutionResult.Status status;
        Entry(String name, TestExecutionResult.Status status) {
            this.name = name;
            this.status = status;
        }
    }

    private final Map<String, List<Entry>> bySuite = new LinkedHashMap<>();

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        this.plan = testPlan;
        bySuite.clear();
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (!testIdentifier.isTest()) return;

        String suite = findSuiteName(testIdentifier);
        bySuite.computeIfAbsent(suite, k -> new ArrayList<>())
                .add(new Entry(testIdentifier.getDisplayName(), testExecutionResult.getStatus()));
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        try {
            writeReport();
        } catch (IOException e) {
            // best-effort: do nothing
        }
    }

    private String findSuiteName(TestIdentifier id) {
        Optional<TestIdentifier> cur = Optional.of(id);
        while (cur.isPresent()) {
            Optional<TestIdentifier> parent = plan.getParent(cur.get());
            if (parent.isEmpty()) break;
            TestIdentifier p = parent.get();
            Optional<TestSource> src = p.getSource();
            if (src.isPresent() && src.get() instanceof ClassSource) {
                String name = p.getDisplayName();
                return name;
            }
            cur = parent;
        }
        return "その他";
    }

    private void writeReport() throws IOException {
        Path out = Path.of("build", "reports", "spec", "spec.md");
        Files.createDirectories(out.getParent());
        var sb = new StringBuilder();
        sb.append("# 仕様レポート（自動生成）\n\n");
        sb.append("- 生成日時: ")
          .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
          .append("\n");
        sb.append("- 実行環境: ").append(System.getProperty("java.version")).append("\n\n");

        for (var e : bySuite.entrySet()) {
            sb.append("## ").append(e.getKey()).append("\n");
            for (Entry entry : e.getValue()) {
                String status = entry.status == TestExecutionResult.Status.SUCCESSFUL ? "成功" :
                        (entry.status == TestExecutionResult.Status.FAILED ? "失敗" : "スキップ");
                sb.append("- ").append(entry.name).append(" … ").append(status).append("\n");
            }
            sb.append("\n");
        }

        Files.writeString(out, sb.toString(), StandardCharsets.UTF_8);
    }
}
