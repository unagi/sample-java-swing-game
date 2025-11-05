# AGENTS.md（開発者向けガイド）

このリポジトリで作業する開発者・エージェント向けの指針です。対象範囲はリポジトリ全体です。

## 言語ポリシー
- コミットメッセージ、プルリクエスト、ドキュメント、アプリ内の表示・ログ・メッセージは日本語で作成してください。

## 環境と実行
- JDK: 25（OpenJDK可）
- ビルド: Gradle（Kotlin DSL推奨）。外部ライブラリは原則なし（標準Swingのみ）。
- 実行: `gradle run`（`application`プラグインを使用）。
- 推奨設定（抜粋）:

```kotlin
plugins {
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    mainClass.set("app.App")
}
```

## コード構成（予定）
- `src/main/java/app/`
  - `App.java`（エントリ）
  - `controller/GameController.java`
  - `model/{Stats.java,Battler.java,Move.java,BattleEngine.java,Gauntlet.java}`
  - `view/{TitleView.java,AllocateView.java,BattleView.java,ResultView.java}`
- `src/main/resources/`（フォント/画像任意）
- `build.gradle.kts`

## アーキテクチャ/データ構造
- Enum `Move { ZAN, TSUKI, UCHI }`（斬/突/打。優劣: ZAN>UCHI, UCHI>TSUKI, TSUKI>ZAN）
- Class `Stats { int power; int skill; int guard; }`
- Class `Battler { String name; int hp; int maxHp; Stats stats; }`
- Class `BattleEngine`:
  - `Outcome compare(Move a, Move b)`（PLUS/MINUS/NONE）
  - `int calcHitPct(Stats atk, Outcome o)`（5〜95%クランプ）
  - `int calcDamage(Stats atk, Stats def, Outcome o)`（最小1）
  - `boolean resolveAttack(attacker, defender, movePair)`（命中ならHP減少）
- 進行管理: `Gauntlet`（5人抜きのラウンド、敵生成、勝利時回復）
- 画面: `TitleView`, `AllocateView`, `BattleView`, `ResultView`（Swingパネル切替）

## コーディング方針
- 仕様のロジックを最優先。不要な抽象化や外部依存を避け、シンプルに実装。
- 例外はUIで握りつぶさず、ユーザーに分かる日本語メッセージで提示。
- 乱数は `java.util.Random` を利用。デバッグ用の固定シード起動を許可。
- UIは640x480固定、キーボード操作（矢印+Enter）に配慮。解決中は入力無効化。
- 将来の拡張（クリティカル、状態異常等）はフックしやすいようにクラス境界を薄く用意。

## テストの観点
- 相性ロジック: 3×3全組み合わせで補正（命中±20pp、ダメージ±5）が正しい。
- 命中率: 技=0/10、相性±、5/95%クランプの端挙動。
- ダメージ: 力/守備の極端値で `max(1, 10 + 3*力 - 2*守備 ± 5)` を満たす。
- 勝敗判定: 先攻で敵撃破時、後攻をスキップ。
- 5人抜き進行: HP持ち越し、勝利時+20回復（上限100）、敗北でゲームオーバー、5体撃破で完全勝利。
- UI: 配分合計（プレイヤー18pt/敵12→16pt）制約、活性/非活性、フォーカス遷移。
- 乱数: 固定シード時に結果が再現される。

## メンテナンス
- 仕様の詳細・ゲームデザインは `README.md` を参照。
- 開発者向けの補助情報（構成、ビルド、テスト観点）は本ファイルを最新に保ってください。
