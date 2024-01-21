package hexapawnRefactorLevel;

public class hxapawn {
	public static void main(String[] args) {
		

		int[] quitLevel = {2, 2}; //左　ゲーム全体を　1:終了　2:続行　/ 右　難易度　1:かんたん　2:ふつう
		quitLevel = title();//タイトル画面。戻り値の値によって始めるか終わるか,また難易度が分岐。
		
		
		int[][] field = new int [3][3];//ボード用。配列field では、　誰もいないマス　＝　0, プレイヤーがいるマス　＝　1, CPUがいるマス　＝　2 が代入される。 
		
		int [][] selectButtun = {{1, 2, 3},{4, 5, 6},{7, 8, 9}}; //プレイヤーの選択コマンドと配列fieldの配置を一致させる。
		
		String [] pawn = {"  ","●","○"}; //UI用。graficsメソッドで使用。　fieldに代入された数字によって、　0 = " ", 1 = "●", 2 = "○"　と、対応して表示させる。
		
		int[][] playerSave = new int[5][2]; //プレイヤーの行動記録用配列。[ラウンド数][移動前のマスの数字、移動後のマスの数字]で記録。
		
		int[][] cpuSave = new int[5][2]; //CPUの行動記録用配列。5にしているのはとりあえずテストでは5ラウンドは越えないから適当にあてている。
		
		int nullCount = 0;	//Cpuのターンで打つ手がなくなった際に無限ループを脱出するためのカウント。
		
		boolean winLose = true;	//勝敗判定用。プレイヤーの勝利：true　試合管理メソッドで止まったタイミングで勝敗を判定する。

			/*配列　selectButtun　と配列　field　の位置イメージは以下の通り。
			 * 
			 *  　　[0] [1] [2]
			 * [2] 7   8   9
			 * [1] 4   5   6
			 * [0] 1   2   3
			 * 
			 * */

		while(quitLevel[0] == 2){ //ゲーム全体を包むループ。一番上で宣言した変数quitの値によって管理。
			
			//ポーンの初期化
			field[1][0] = 0; //4のマスを初期化
			field[1][1] = 0; //5のマスを初期化
			field[1][2] = 0; //6のマスを初期化
			
			//ポーン配置
			field[2][0] = 2; //7のマスにCPUのポーンを配置
			field[2][1] = 2; //8のマスにCPUのポーンを配置
			field[2][2] = 2; //9のマスにCPUのポーンを配置
			
			field[0][0] = 1; //１のマスにplayerのポーンを配置
			field[0][1] = 1; //2のマスにplayerのポーンを配置
			field[0][2] = 1; //3のマスにplayerのポーンを配置
			

			int round = 1;	//ラウンド回数。ラウンドの表示や、打ち手を記録する際に配列の添え字として使用。
			
			while(true) {
				
				System.out.println("ラウンド" + round);//現時点でのラウンドナンバーの表示
				
				//初期位置の表示。（初回限定にしないとCPUが勝った時に余分に出てしまう。）
				if(round == 1) { 
				System.out.println();
				grafics(field, pawn);//戦況表示
				};
				
			//--------->プレイヤーのターン			
				
				//行動・記録
				playerSave [(round - 1)] = playerTurn(field, selectButtun);
				
				grafics(field, pawn);//戦況表示
				
				//試合管理。どちらかが勝利条件を満たしたとき、その時点で試合を止める。ここでとまると winLose == true
				if(winLose(field, nullCount)) {
					break;
				}
				winLose = false;//勝敗判定用。falseで止まればCPUの勝利。
				

				
			//--------->CPUのターン			
	
				//行動・記録
				int [] x = null;
				
				//CpuTurnメソッドからの戻り値が配列なのを利用し、有効な手が見つからなかった場合はnullで返し、その時はもう一度行動をやり直す。
				while(x == null && nullCount <= 1000) { //有効打が見つからなかったとき
					
					if(quitLevel[1] == 2) { //難易度によって参照クラスが分岐する
						x = CpuTurn.CpuTurn(field, selectButtun, round);
					}else {
						x = CpuTurnEasy.CpuTurnEasy(field, selectButtun, round);
					}
					
					nullCount++; //有効な手が見つからなかった場合をカウント。
					if(x != null) { //有効打が見つかった場合
						cpuSave[(round - 1)] = x; //記録
						grafics(field, pawn); //戦況表示
						
					}else if(nullCount > 1000) { //一定の回数有効打が見つからない場合、プレイヤーが「勝利条件:相手の有効打をなくす」を満たしたことにする。(無限ループ回避)なお、回数は勘で設定しているのでガバい。
						winLose = true;
						
					}
				}
				
				//勝敗判定。どちらかが条件を満たしたとき、その時点で試合を止める。
				if(winLose(field, nullCount)) {
					break;
				}
				winLose = true; //勝敗判定用。trueで止まればプレイヤーの勝利。
				
				round++;//ラウンド回数を増やす。
			}
			
			//勝敗判定
			if(winLose == true) {
				System.out.println("白の勝利です！！");
			}else {
				System.out.println("黒の勝利です！！");
			}
			
			//ゲームの続行
			System.out.println("終了する場合は'q'、続ける場合は他のキーを押してください");
			String q = new java.util.Scanner(System.in).nextLine();
			
			if(q.equals("q")) {
				quitLevel[0] = 1;
				System.out.println("ゲームを終了します。");
			}
			
		} //while(quit == 2)をとじる

		System.exit(1);
	} //mainメソッド
	
//以下、メソッド----------->
	
	//プレイヤーのターン　メソッド
	public static int[] playerTurn(int[][] field, int[][] selectButtun) {
		
		//プレイヤーの入力
		System.out.println();
		System.out.print("駒を選んでください(1～9)：");
		int selectP = new java.util.Scanner(System.in).nextInt();
		System.out.print("どこに動かしますか？(1～9)：");
		int selectF = new java.util.Scanner(System.in).nextInt();
		System.out.println();
		
		//ポーン移動　フィールドを一つずつチェックしていき、プレイヤーが選択したマスと重なった時にルールを満たしていれば移動をおこなう。
		for(int i = 0; i < field.length; i++) {
			for(int j = 0; j < field[0].length; j++) {
				
				
				//動かすポーンの元の位置を削除
				if(selectButtun[i][j] == selectP) {
					field[i][j] = 0;
		
				}
				
				//移動先にポーンを出現させる
				if(selectButtun[i][j] == selectF && selectF == selectP + 3 && field[i][j] == 0) { //ひとつ前に進めるときの処理
					field[i][j] = 1; 
					int [] playerSave = {selectF, selectP}; //記録用戻り値
					return playerSave;
					
				}else if (selectButtun[i][j] == selectF && field[i][j] == 2) { //移動先に敵のポーンがある場合の処理　斜め前にあるときのみ移動可能
					switch(selectP) {
						case 1:
						case 4:
							if(selectF == selectP + 4) {
								field[i][j] = 1;
								int [] playerSave = {selectF, selectP}; //記録用戻り値
								return playerSave;
							}
							break;
						case 2:
						case 5:
							if(selectF == selectP + 2 || selectF == selectP + 4) {
								field[i][j] = 1;
								int [] playerSave = {selectF, selectP}; //記録用戻り値
								return playerSave;

							}
							break;
						case 3:
						case 6:
							if(selectF == selectP + 2){
								field[i][j] = 1;
								int [] playerSave = {selectF, selectP}; //記録用戻り値
								return playerSave;
							}
					}
				}
			}
		}
		System.out.println("その手は選べません。もう一度選択してください。");
		return playerTurn(field, selectButtun);	
	}
		
//*************************	
	
	//盤面表示メソッド
	public static void grafics(int[][] field, String[] pawn) {

		
		System.out.println("  +----+----+----+");
		System.out.println("3 | " + pawn[field[2][0]] + " | " + pawn[field[2][1]] + " | " + pawn[field[2][2]] + " |	 7  8  9");
		System.out.println("  +----+----+----+");
		System.out.println("2 | " + pawn[field[1][0]] + " | " + pawn[field[1][1]] + " | " + pawn[field[1][2]] + " |	 4  5  6");
		System.out.println("  +----+----+----+");
		System.out.println("1 | " + pawn[field[0][0]] + " | " + pawn[field[0][1]] + " | " + pawn[field[0][2]] + " |	 1  2  3");
		System.out.println("  +----+----+----+");
		System.out.println("     a 	  b   c");
	}

//*************************	
		
	//試合管理メソッド：勝利条件が満たされた場合、試合を止めて結果発表へ　true:誰かが勝利条件を満たした
	public static boolean winLose(int[][]field, int nullCount) {
		
		//プレイヤーの勝利時
		if((field[2][0] == 1 || field[2][1] == 1 || field[2][2] == 1)	//敵陣までプレイヤーが到達した時
//			|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[2][0] == 2 && field[2][2] == 2 && field[1][0] == 1 && field[1][2] == 1))//CPUの打つ手がなくなった時
//			|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[1][0] == 2 && field[1][2] == 2 && field[0][0] == 1 && field[0][2] == 1))
//			|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[1][0] == 2 && field[2][2] == 2 && field[0][0] == 1 && field[1][2] == 1))
//			|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[2][0] == 2 && field[1][2] == 2 && field[1][0] == 1 && field[0][2] == 1))
			|| nullCount > 1000 //今回はnullCountの回数で代用している。
			|| (field[2][0] != 2 && field[2][1] != 2 && field[2][2] != 2//敵の駒をすべて取った時
			&& field[1][0] != 2 && field[1][1] != 2 && field[1][2] != 2
			&& field[0][0] != 2 && field[0][1] != 2 && field[0][2] != 2)) { 
			return true;
		
		//CPUの勝利時
		}else if((field[0][0] == 2 || field[0][1] == 2 || field[0][2] == 2)	//敵陣までCPUが到達した時
				|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[2][0] == 2 && field[2][2] == 2 && field[1][0] == 1 && field[1][2] == 1))//プレイヤーの打つ手がなくなった時
				|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[1][0] == 2 && field[1][2] == 2 && field[0][0] == 1 && field[0][2] == 1))
				|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[1][0] == 2 && field[2][2] == 2 && field[0][0] == 1 && field[1][2] == 1))
				|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[2][0] == 2 && field[1][2] == 2 && field[1][0] == 1 && field[0][2] == 1))
				
				|| ((field[2][0] == 0 && field[1][0] == 0 && field[0][0] == 0) && (field[2][1] == 2 && field[1][1] == 1 && field[1][2] == 2 && field[0][2] == 1))//かんたんモードの場合は追加
				|| ((field[2][2] == 0 && field[1][2] == 0 && field[0][2] == 0) && (field[2][1] == 2 && field[1][1] == 1 && field[1][0] == 2 && field[0][0] == 1))
				|| ((field[2][0] == 0 && field[1][0] == 0 && field[0][0] == 0) && (field[1][1] == 2 && field[0][1] == 1 && field[2][2] == 2 && field[1][2] == 1))
				|| ((field[2][2] == 0 && field[1][2] == 0 && field[0][2] == 0) && (field[1][1] == 2 && field[0][1] == 1 && field[2][0] == 2 && field[1][0] == 1))
				
//	
//				  +----+----+----+
//				3 |  ○  |    |  ○  | 7  8  9
//				  +----+----+----+
//				2 |  ●  |    |  ● |	 4  5  6　　　　　これらの左右上下反転の場合
//				  +----+----+----+
//				1 |    |     |    |	 1  2  3
//				  +----+----+----+
//				     a 	  b   c
//				
//			     	+----+----+----+
//				  3 |  ○  |    |    |	 7  8  9
//				    +----+----+----+
//				  2 |  ●  |  ○  |   |	 4  5  6
//				    +----+----+----+
//				  1 |    |  ●  |    |	 1  2  3
//				    +----+----+----+
//				       a 	  b   c
				
				
				
				
				|| (field[2][0] != 1 && field[2][1] != 1 && field[2][2] != 1 //敵の駒をすべて取った時
				&&  field[1][0] != 1 && field[1][1] != 1 && field[1][2] != 1
				&&  field[0][0] != 1 && field[0][1] != 1 && field[0][2] != 1)){ 
			return true;
		}else {
			return false;
		}
	}

//*************************	

	public static int[] title() {
		
		//タイトル表示
		System.out.println("    ●　　●  ●●●● 　 ●　　●　    ●●    　 ●●●　 　  ●● 　　  ●　　  ●　  ●　　●");
		System.out.println("   ●　　●  ●　　　 　 　●●       ●　　●　　●　　●　　●　　●   ●  ●  ●　  ●　●●");
		System.out.println("  ●●●●  ●●●●　　  ●●      ●●●●  　●●●●  　●●●●　  ● ●　●　  ●●●●");
		System.out.println(" ●　　●　●　　　　　 ●●　    ●　　●  　●　　　 　●　　●　  ●　   ●　　 ●●　●");
		System.out.println("●　　●　●●●●　 ●　　●　 ●　　●  　●　 　　　●　　●　  ●　   ●　 　●　　●");
		System.out.println();
		
		//ゲームを始める
		System.out.println("はじめる？( \"y\" / \"n\" / \"e\" )");
		System.out.println("※eはかんたんモード");
		System.out.print(">");
		String start = new java.util.Scanner(System.in).nextLine();
		
		int[] ql = {2,2}; //続行管理
		int level = 2; //難易度選択
		if(start.equals("n")) {
			System.out.println("ゲームを終了します。");
			ql[0] = 1;
			return ql;
		}else if(start.equals("e")){
			ql[1] = option();
			rule(ql[1]);
			return ql;
		}else{
			rule(ql[1]); //ルール説明
			return ql; 
			
		}
		
	}
	public static void rule(int level) {
		
		
		//ルール説明１
		String[] rule1 = {
			"== ゲームのルール説明 ==",
			"チェスを簡略化したゲームです。",
			"３×３の盤上で白黒それぞれ三つの駒（ポーン）を使用します。",
			"白（手前） = プレイヤー、 黒(奥) = CPU　で、プレイヤーが先行です。",
			" ",
			"== 駒の動き ==",
			"・前のマスに誰もいないときは1マス前に進める。",
			"・斜め前に相手の駒があるときは取ることができる。",
			" ",
			"== 勝利条件 ==",
			"1.３マス目に自分の駒を進める。",
			"2.相手の駒を全て取る。",
			"3.相手の駒が動けないような配置にする。"
		};
		
		//ルール説明１(かんたんモード)
		String[] rule1Easy = {
			"== ゲームのルール説明 ==",
			"チェスを簡略化したゲームです。",
			"３×３の盤上で白黒それぞれ三つの駒（ポーン）を使用します。",
			"白（手前） = プレイヤー、 黒(奥) = CPU　で、プレイヤーが先行です。",
			"かんたんモード発動！",
			"相手がちょっと弱くなるかも！？",
			" ",
			"== 駒の動き ==",
			"・前のマスに誰もいないときは1マス前に進める。",
			"・斜め前に相手の駒があるときは取ることができる。",
			" ",
			"== 勝利条件 ==",
			"1.３マス目に自分の駒を進める。",
			"2.相手の駒を全て取る。",
			"3.相手の駒が動けないような配置にする。"
		};
		
		switch(level) {
			case 1://かんたんver.
				//文が入った配列を順番に表示(ゆっくり回せたら演出になるかも)
				for (String str1 : rule1Easy) {
					System.out.println(str1);
				}
				break;
			case 2:
				for (String str1 : rule1) {
					System.out.println(str1);
				}
				break;
		}

		
		//ページ分割
		System.out.println();
		System.out.println("次へ [Enter]　");
		String next = new java.util.Scanner(System.in).nextLine();
		
		//ルール説明2
		String[] rule2 = {
				"== 画面の説明 ==",
				"											",
				"	  ボード		 　マスに対応した数字	",
				"		/	  ポーン						",
				"  +----+----+--|-+　  	   　/				",
				"3 | ○ | ○ | ○ |	 7  8  9				",
				"  +----+----+----+							",
				"2 |    |    |    |	 4  5  6				",
				"  +----+----+----+				",
				"1 | ● | ● | ● |	 1  2  3",
				"  +----+----+----+	",
				"     a    b    c",
				"駒を選んでください(1～9)：	 <--①ここに動かしたい駒のあるマスに対応した数字を入力",
				"どこに動かしますか？(1～9)：　<--②ここに移動したいマスに対応した数字を入力",
				"											",
				"CPU： c3をc2へ   <--③プレイヤーが入力した後、CPUが行動"				
		};
		
		//文が入った配列を順番に表示
		for (String str2 : rule2) {
			System.out.println(str2);
		}
		
		System.out.println();
		System.out.println("準備ができたら　[Enter]　を押してね");
		String go = new java.util.Scanner(System.in).nextLine();

	}
	
	public static int option() {
		System.out.println("=かんたんモード=");
		System.out.println("かんたんモードであそびますか？");
		System.out.println("かんたんモードで遊ぶ場合は'y'、普通モードで遊ぶ場合は他のキーを押してください\"");
		String level = new java.util.Scanner(System.in).nextLine();
		
		if(level.equals("y")) {
			return 1;
		}else {
			return 2;
		}
	}
}
