# AGENTS.md・磯幕逋ｺ閠・髄縺代ぎ繧､繝会ｼ・
縺薙・繝ｪ繝昴ず繝医Μ縺ｧ菴懈･ｭ縺吶ｋ髢狗匱閠・・繧ｨ繝ｼ繧ｸ繧ｧ繝ｳ繝亥髄縺代・謖・・縺ｧ縺吶ょｯｾ雎｡遽・峇縺ｯ繝ｪ繝昴ず繝医Μ蜈ｨ菴薙〒縺吶・
## 險隱槭・繝ｪ繧ｷ繝ｼ
- 繧ｳ繝溘ャ繝医Γ繝・そ繝ｼ繧ｸ縲√・繝ｫ繝ｪ繧ｯ繧ｨ繧ｹ繝医√ラ繧ｭ繝･繝｡繝ｳ繝医√い繝励Μ蜀・・陦ｨ遉ｺ繝ｻ繝ｭ繧ｰ繝ｻ繝｡繝・そ繝ｼ繧ｸ縺ｯ譌･譛ｬ隱槭〒菴懈・縺励※縺上□縺輔＞縲・
## 迺ｰ蠅・→螳溯｡・- JDK: 25・・penJDK蜿ｯ・・- 繝薙Ν繝・ Gradle・・otlin DSL謗ｨ螂ｨ・峨ょ､夜Κ繝ｩ繧､繝悶Λ繝ｪ縺ｯ蜴溷援縺ｪ縺暦ｼ域ｨ呎ｺ亡wing縺ｮ縺ｿ・峨・- 螳溯｡・ `gradle run`・・application`繝励Λ繧ｰ繧､繝ｳ繧剃ｽｿ逕ｨ・峨・- 謗ｨ螂ｨ險ｭ螳夲ｼ域栢邊具ｼ・

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

## 繧ｳ繝ｼ繝画ｧ区・・井ｺ亥ｮ夲ｼ・- `src/main/java/app/`
  - `App.java`・医お繝ｳ繝医Μ・・  - `controller/GameController.java`
  - `model/{Stats.java,Battler.java,Move.java,BattleEngine.java,Gauntlet.java}`
  - `view/{TitleView.java,AllocateView.java,BattleView.java,ResultView.java}`
- `src/main/resources/`・医ヵ繧ｩ繝ｳ繝・逕ｻ蜒丈ｻｻ諢擾ｼ・- `build.gradle.kts`

## 繧｢繝ｼ繧ｭ繝・け繝√Ε/繝・・繧ｿ讒矩
- Enum `Move { ZAN, TSUKI, UCHI }`・域脈/遯・謇薙ょ━蜉｣: ZAN>UCHI, UCHI>TSUKI, TSUKI>ZAN・・- Class `Stats { int power; int skill; int guard; }`
- Class `Battler { String name; int hp; int maxHp; Stats stats; }`
- Class `BattleEngine`:
  - `Outcome compare(Move a, Move b)`・・LUS/MINUS/NONE・・  - `int calcHitPct(Stats atk, Outcome o)`・・縲・5%繧ｯ繝ｩ繝ｳ繝暦ｼ・  - `int calcDamage(Stats atk, Stats def, Outcome o)`・域怙蟆・・・  - `boolean resolveAttack(attacker, defender, movePair)`・亥多荳ｭ縺ｪ繧羽P貂帛ｰ托ｼ・- 騾ｲ陦檎ｮ｡逅・ `Gauntlet`・・莠ｺ謚懊″縺ｮ繝ｩ繧ｦ繝ｳ繝峨∵雰逕滓・縲∝享蛻ｩ譎ょ屓蠕ｩ・・- 逕ｻ髱｢: `TitleView`, `AllocateView`, `BattleView`, `ResultView`・・wing繝代ロ繝ｫ蛻・崛・・
## 繧ｳ繝ｼ繝・ぅ繝ｳ繧ｰ譁ｹ驥・- 莉墓ｧ倥・繝ｭ繧ｸ繝・け繧呈怙蜆ｪ蜈医ゆｸ崎ｦ√↑謚ｽ雎｡蛹悶ｄ螟夜Κ萓晏ｭ倥ｒ驕ｿ縺代√す繝ｳ繝励Ν縺ｫ螳溯｣・・- 萓句､悶・UI縺ｧ謠｡繧翫▽縺ｶ縺輔★縲√Θ繝ｼ繧ｶ繝ｼ縺ｫ蛻・°繧区律譛ｬ隱槭Γ繝・そ繝ｼ繧ｸ縺ｧ謠千､ｺ縲・- 荵ｱ謨ｰ縺ｯ `java.util.Random` 繧貞茜逕ｨ縲ゅョ繝舌ャ繧ｰ逕ｨ縺ｮ蝗ｺ螳壹す繝ｼ繝芽ｵｷ蜍輔ｒ險ｱ蜿ｯ縲・- UI縺ｯ640x480蝗ｺ螳壹√く繝ｼ繝懊・繝画桃菴懶ｼ育泙蜊ｰ+Enter・峨↓驟肴・縲りｧ｣豎ｺ荳ｭ縺ｯ蜈･蜉帷┌蜉ｹ蛹悶・- 蟆・擂縺ｮ諡｡蠑ｵ・医け繝ｪ繝・ぅ繧ｫ繝ｫ縲∫憾諷狗焚蟶ｸ遲会ｼ峨・繝輔ャ繧ｯ縺励ｄ縺吶＞繧医≧縺ｫ繧ｯ繝ｩ繧ｹ蠅・阜繧定埋縺冗畑諢上・
## 繝・せ繝医・隕ｳ轤ｹ
- 逶ｸ諤ｧ繝ｭ繧ｸ繝・け: 3ﾃ・蜈ｨ邨・∩蜷医ｏ縺帙〒陬懈ｭ｣・亥多荳ｭﾂｱ20pp縲√ム繝｡繝ｼ繧ｸﾂｱ5・峨′豁｣縺励＞縲・- 蜻ｽ荳ｭ邇・ 謚=0/10縲∫嶌諤ｧﾂｱ縲・/95%繧ｯ繝ｩ繝ｳ繝励・遶ｯ謖吝虚縲・- 繝繝｡繝ｼ繧ｸ: 蜉・螳亥ｙ縺ｮ讌ｵ遶ｯ蛟､縺ｧ `max(1, 10 + 3*蜉・- 2*螳亥ｙ ﾂｱ 5)` 繧呈ｺ縺溘☆縲・- 蜍晄風蛻､螳・ 蜈域判縺ｧ謨ｵ謦・ｴ譎ゅ∝ｾ梧判繧偵せ繧ｭ繝・・縲・- 5莠ｺ謚懊″騾ｲ陦・ HP謖√■雜翫＠縲∝享蛻ｩ譎・20蝗槫ｾｩ・井ｸ企剞100・峨∵風蛹励〒繧ｲ繝ｼ繝繧ｪ繝ｼ繝舌・縲・菴捺茶遐ｴ縺ｧ螳悟・蜍晏茜縲・- UI: 驟榊・蜷郁ｨ茨ｼ医・繝ｬ繧､繝､繝ｼ18pt/謨ｵ12竊・6pt・牙宛邏・∵ｴｻ諤ｧ/髱樊ｴｻ諤ｧ縲√ヵ繧ｩ繝ｼ繧ｫ繧ｹ驕ｷ遘ｻ縲・- 荵ｱ謨ｰ: 蝗ｺ螳壹す繝ｼ繝画凾縺ｫ邨先棡縺悟・迴ｾ縺輔ｌ繧九・
## 繝｡繝ｳ繝・リ繝ｳ繧ｹ
- 莉墓ｧ倥・隧ｳ邏ｰ繝ｻ繧ｲ繝ｼ繝繝・じ繧､繝ｳ縺ｯ `README.md` 繧貞盾辣ｧ縲・- 髢狗匱閠・髄縺代・陬懷勧諠・ｱ・域ｧ区・縲√ン繝ｫ繝峨√ユ繧ｹ繝郁ｦｳ轤ｹ・峨・譛ｬ繝輔ぃ繧､繝ｫ繧呈怙譁ｰ縺ｫ菫昴▲縺ｦ縺上□縺輔＞縲・
