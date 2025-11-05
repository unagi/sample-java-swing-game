# AGENTS.md（開発者向けガイド）

このリポジトリで作業する開発者・エージェント向けの指針です。対象範囲はリポジトリ全体です。

## 言語ポリシー
- コミットメッセージ、プルリクエスト、ドキュメント、アプリ内の表示・ログ・メッセージは日本語で作成してください。

## 環境とビルド
- JDK: 25（Eclipse Temurin 等）。
- Gradle: Wrapper 9.0 を同梱。必ず `gradlew`/`gradlew.bat` を使用してください。
- ビルドスクリプト: Groovy DSL（`build.gradle` / `settings.gradle`）。
- 実行:
  - Windows: `./gradlew-utf8.bat run`（推奨）または `./gradlew-utf8.ps1 run`
  - macOS/Linux: `./gradlew run`
- 配布:
  - Zip作成: `./gradlew packageDist` → `build/distributions/tri-battle-gauntlet.zip`
  - ローカルインストール: `./gradlew installDist`
  - 起動ヘルパー: `run.bat` / `run.sh`
- エンコーディング: すべて UTF-8。Java実行時は `-Dfile.encoding=UTF-8` を付与。

## PowerShellで文字化けしない秘訣（重要）
- 実行はUTF-8ラッパー経由を推奨
  - PowerShell: `./gradlew-utf8.ps1 test` / `run`
  - CMD/PowerShell: `./gradlew-utf8.bat test` / `run`
- コンソール出力をUTF-8に設定（PowerShell）
  - `[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding`
- Java/GradleにUTF-8を伝える
  - `$env:JAVA_TOOL_OPTIONS = "$($env:JAVA_TOOL_OPTIONS) -Dfile.encoding=UTF-8 --enable-native-access=ALL-UNNAMED"`
  - `$env:GRADLE_OPTS      = "$($env:GRADLE_OPTS) -Dfile.encoding=UTF-8"`
- ファイル入出力（PowerShell）でUTF-8を明示
  - 読み込み: `Get-Content -Encoding UTF8 <path>`
  - 書き込み: `Set-Content -Encoding UTF8 <path> <text>` / `Out-File -Encoding utf8 <path>`
  - .NET API例: `[System.IO.File]::WriteAllText(<path>, <text>, [System.Text.UTF8Encoding]::new($false))`
- 文字化けの典型原因と回避
  - 既定コードページ（CP932/OEM）でのパイプ/リダイレクト → 上記のUTF-8設定を先に実施
  - BOMの有無不一致 → UTF-8を明示、必要に応じてBOM付与/なしを使い分け
  - 非UTF-8フォント/端末 → 日本語対応フォント＋UTF-8コードページの端末を使用

## フォルダ構成
- `src/main/java/app/`
  - `App.java`（エントリ）
  - `controller/GameController.java`
  - `model/{Stats.java,Battler.java,Move.java,Outcome.java,AttackResult.java,BattleEngine.java,Gauntlet.java}`
  - `view/{TitleView.java,AllocateView.java,BattleView.java,ResultView.java}`
- `build.gradle` / `settings.gradle`
- `gradle/`（Wrapper一式）、`gradlew` / `gradlew.bat`
- `README.md`（仕様/操作）、`AGENTS.md`（開発向け）、`run.bat` / `run.sh`

## アーキテクチャ/データ構造
- Enum `Move { ZAN, TSUKI, UCHI }`（斬/突/打。優劣: ZAN>UCHI, UCHI>TSUKI, TSUKI>ZAN）
- Enum `Outcome { PLUS, MINUS, NONE }`
- Class `Stats { int power; int skill; int guard; }`
- Class `Battler { String name; int hp; int maxHp; Stats stats; }`
- Class `BattleEngine`:
  - 命中率: `70 + skill*2` を相性で ±20pp、5〜95%にクランプ
  - ダメージ: `max(1, (10 + power*3 ±5) - guard*2)`
  - 攻撃解決: `attemptAttack(attacker, defender, outcome, name)`
- Class `Gauntlet`（5人抜き: 敵ポイント合計 12→16、勝利時HP+20）
- 画面: `TitleView`/`AllocateView`/`BattleView`/`ResultView`（CardLayout 切替）
- 制御: `GameController`（状態遷移・入力管理）

## コーディング方針
- シンプル・最小依存（外部ライブラリ無し）。仕様ロジックを最優先。
- UIは640x480固定。解決中の多重入力は無効化。
- メッセージは日本語。例外は握りつぶさずユーザーに分かる表現で通知。
- 乱数は `java.util.Random`。固定シードでの再現性も確保（将来拡張）。
- 将来拡張（クリティカル、状態異常等）を見越し、クラス境界を素直に分離。

## テスト観点（目安）
- 相性ロジック: 3×3全組み合わせで命中±20pp/ダメージ±5 を確認。
- 命中率: 技=0/10、相性±でクランプ5/95%の端挙動。
- ダメージ: 力/守備極端値で最小1保証、相性補正の反映。
- 勝敗判定: 先攻撃破で後攻スキップ。
- 5人抜き: HP持ち越し、勝利時+20回復、敗北で終了、5体撃破で完全勝利。
- UI: 配分合計（18pt）制約、活性/非活性、フォーカス遷移。
- 乱数: 固定シード時に結果が再現される。

## メンテナンス
- 仕様/ゲームデザインの一次情報は `README.md`。
- 本ファイルは開発運用（構成/ビルド/テスト観点/方針）を最新に保つこと。
- ランタイム/Wrapperの更新方針: 9.0を既定。将来の9.1+公開状況を確認し、安定版公開後にWrapper更新。

