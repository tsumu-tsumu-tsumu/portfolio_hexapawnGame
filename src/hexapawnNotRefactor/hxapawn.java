//ゲームのルール
//チェスを簡略化したゲームです。
//
//３×３の盤上で白黒それぞれ三つの駒（ポーン）を使用します。白の先手で交互に一手ずつ指すのはチェスと同じです。
//
//駒の動き
//１マス前に進む。
//斜め前に相手の駒があるときは取ることができる。

//勝敗の決め方
//　次の条件に当てはまった側が勝ちとなります。
//
//３マス目に自分の駒を進める。
//相手の駒を全て取る。
//相手の駒が動けないような配置にする。


//・CPUの打つ手がないときに無限ループになる


package hexapawnNotRefactor;

public class hxapawn {
	public static void main(String[] args) {
		

		int quit = 2;//ゲームを　1:終了　2:続行
		quit = title();//タイトル画面
//		System.out.println("★");//テスト用
		
		//盤上を用意　	配列field では、　誰もいないマス　＝　0, プレイヤーがいるマス　＝　1, CPUがいるマス　＝　2 が代入される。 
		int[][] field = new int [3][3];
		//プレイヤーの選択コマンドと配列fieldの配置を一致させる。
		int [][] selectButtun = {{1, 2, 3},{4, 5, 6},{7, 8, 9}};
		//UI用。graficsメソッドで使用。　fieldに代入された数字が、　0 = " ", 1 = "●", 2 = "○"　で対応して表示させる。
		String [] pawn = {"  ","●","〇"}; 
		int[][] playerSave = new int[10][2];	//行動の記録用。ターン数の上限はとりあえず10回にしてる
		int[][] cpuSave = new int[10][2];	//CPUの行動記録用配列。
		//Cpuのターンで打つ手がなくなった際に無限ループ脱出用のカウント。
		int nullCount = 0;
		//勝敗判定用。プレイヤーの勝利：true　試合続行管理メソッドで止まったタイミングで勝敗を分ける。
		boolean winLose = true;

			/*配列　selectButtun　と配列　field　の位置イメージは以下の通り。
			 * 
			 *       [0] [1] [2]
			 * 3  [2] 7   8   9
			 * 2  [1] 4   5   6
			 * 1  [0] 1   2   3
			 * 
			 * 	     a   b   c
			 * 
			 * */
			
			
		while(quit == 2){
			//ポーンの初期化
			field[1][0] = 0;
			field[1][1] = 0;
			field[1][2] = 0;
			
			//ポーンを配置
			field[2][0] = 2; 	//フィールドのa3にCPUのポーンを配置
//			int[] cpu1 = {2,0}; //cpuのポーン1号として位置を記録
			field[2][1] = 2; 	//フィールドのb3にCPUのポーンを配置
//			int[] cpu2 = {2,1}; //cpuのポーン2号として位置を記録
			field[2][2] = 2; 	//フィールドのc3にCPUのポーンを配置
//			int[] cpu3 = {2,2}; //cpuのポーン3号として位置を記録
			
			field[0][0] = 1; 		//フィールドのa1にplayerのポーンを配置
//			int[] player1 = {0,0};  //playerのポーン1号として位置を記録
			field[0][1] = 1;		//フィールドのb1にplayerのポーンを配置
//			int[] player2 = {0,1};  //playerのポーン2号として位置を記録
			field[0][2] = 1;        //フィールドのc1にplayerのポーンを配置
//			int[] player3 = {0,2};  //playerのポーン3号として位置を記録
			

			int round = 1;	//ラウンド回数
			
			while(true) {
				
				System.out.println("ラウンド" + round);
				if(round == 1) {
				System.out.println();
				grafics(field, pawn);//戦況表示
				};
				
				//プレイヤーのターン****
	//			int selectP = 0;
	//			int selectF = 0;
			
	
					//プレイヤーの入力
	//				System.out.println();
	//				System.out.print("駒を選んでください(1～9)：");
	//				selectP = new java.util.Scanner(System.in).nextInt();
	//				System.out.print("どこに動かしますか？(1～9)：");
	//				selectF = new java.util.Scanner(System.in).nextInt();
	//				System.out.println()
					
					
	//				}while(playerTurn(field, selectButtun, playerCou, selectP, selectF) == 10);  //プレイヤーが間違った手をうった場合のやりなおし
				
				
				//行動・記録
				playerSave [(round - 1)] = playerTurn(field, selectButtun);
	//			playerSave[i][0] = selectF; //後でひっくり返す
	//			playerSave[i][1] = selectP;
				
				//プレイヤーのターン終了****
				
				grafics(field, pawn);//戦況表示
				
				//勝敗判定。どちらかが条件を満たしたとき、その時点で試合を止める。
				if(winLose(field, nullCount)) {
					break;
				}
				winLose = false;//勝敗判定用。falseで止まればCPUの勝利。
				

				
				//CPUのターン****			
	//			int cpuCou = 0; //PCのターン回数：カウント用
	
				//行動・記録
	//			cpuTurn(field, selectButtun);	//行動
				int [] x = null;
				while(x == null && nullCount <= 1000) {
//					System.out.println("★");//テスト用
					x = CpuTurn.CpuTurn(field, selectButtun, round, nullCount);
					nullCount++;
					
					if(x != null) {
						cpuSave[(round - 1)] = x; //記録
						grafics(field, pawn);//戦況表示
						
					}else if(nullCount > 1000) {
						winLose = true;	//CPUが打つ手がなくなった時はプレイヤーの勝利だが、クラスから帰ってくる位置は変えられない。
						
					}
				}
				

				
				//勝敗判定。どちらかが条件を満たしたとき、その時点で試合を止める。
				if(winLose(field, nullCount)) {
					break;
				}
				winLose = true;//勝敗判定用。trueで止まればプレイヤーの勝利。
				//****
				
				round++;//ラウンド回数を増やす。
			}
			
			//勝敗判定とゲームの続行
			if(winLose == true) {
				System.out.println("白の勝利です！！");
			}else {
//				grafics(field, pawn);//戦況表示
				System.out.println("黒の勝利です！！");
			}
			
			System.out.println("終了する場合は'q'、続ける場合は他のキーを押してください");
			String q = new java.util.Scanner(System.in).nextLine();
			
			if(q.equals("q")) {
				quit = 1;
				System.out.println("ゲームを終了します。");
			}
			
		}
//		System.out.println("オフ");//テスト用
		System.exit(1);
	}
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

							}//else {
//								System.out.println("その手は選べません。もう一度選択してください。");
//								playerTurn(field, selectButtun); //量が多いとオーバーフローするので、再帰じゃない方がいいかも・・・
//							}
							break;
						case 2:
						case 5:
							if(selectF == selectP + 2 || selectF == selectP + 4) {
								field[i][j] = 1;
								int [] playerSave = {selectF, selectP}; //記録用戻り値
								return playerSave;

							}//else {
//								System.out.println("その手は選べません。もう一度選択してください。");
//								playerTurn(field, selectButtun);

//							}
							break;
						case 3:
						case 6:
							if(selectF == selectP + 2){
								field[i][j] = 1;
								int [] playerSave = {selectF, selectP}; //記録用戻り値
								return playerSave;
							}//else {
//								System.out.println("その手は選べません。もう一度選択してください。");
//								playerTurn(field, selectButtun);
//						
//							}
//							break;
//						default:
//							System.out.println("その手は選べません。もう一度選択してください。");
//							playerTurn(field, selectButtun);
				
					}
					
				}//else if (selectButtun[i][j] == selectF){ //選べない手を選んだ時の処理
//					System.out.println("その手は選べません。もう一度選択してください。");
//					playerTurn(field, selectButtun);
		
				}


			}
		System.out.println("その手は選べません。もう一度選択してください。");
		return playerTurn(field, selectButtun);
			
		}
	
//	//プレイヤーのターン　メソッド 最後の処理で戻り値を返す
//	public static int[] playerTurn(int[][] field, int[][] selectButtun) {
////		playerCou++;//回数カウント　必要？？
//		
//		System.out.println();
//		System.out.print("駒を選んでください(1～9)：");
//		int selectP = new java.util.Scanner(System.in).nextInt();
//		System.out.print("どこに動かしますか？(1～9)：");
//		int selectF = new java.util.Scanner(System.in).nextInt();
//		System.out.println();
//		
//		//ポーン移動
//		for(int i = 0; i < field.length; i++) {
//			for(int j = 0; j < field[0].length; j++) {
//				
//				
//				//動かすポーンの元の位置を削除
//				if(selectButtun[i][j] == selectP) {
//					field[i][j] = 0;
//		
//				}
//				
//				//移動先にポーンを出現させる
//				if(selectButtun[i][j] == selectF && selectF == selectP + 3 && field[i][j] == 0) { //ひとつ前に進めるときの処理
//					field[i][j] = 1; 
//					
//				}else if (selectButtun[i][j] == selectF && field[i][j] == 2) { //移動先に敵のポーンがある場合の処理　斜め前にあるときのみ移動可能
//					switch(selectP) {
//						case 1:
//						case 4:
//							if(selectF == selectP + 4) {
//								field[i][j] = 1;
//
//							}else {
//								System.out.println("その手は選べません。もう一度選択してください。");
//								playerTurn(field, selectButtun);
//							}
//							break;
//						case 2:
//						case 5:
//							if(selectF == selectP + 2 || selectF == selectP + 4) {
//								field[i][j] = 1;
//
//							}else {
//								System.out.println("その手は選べません。もう一度選択してください。");
//								playerTurn(field, selectButtun);
//
//							}
//							break;
//						case 3:
//						case 6:
//							if(selectF == selectP + 2) {
//								field[i][j] = 1;
//	
//							}else {
//								System.out.println("その手は選べません。もう一度選択してください。");
//								playerTurn(field, selectButtun);
//						
//							}
//							break;
//						default:
//							System.out.println("その手は選べません。もう一度選択してください。");
//							playerTurn(field, selectButtun);
//				
//					}
//					
//				}else if (selectButtun[i][j] == selectF){ //選べない手を選んだ時の処理
//					System.out.println("その手は選べません。もう一度選択してください。");
//					playerTurn(field, selectButtun);
//		
//				}
//
//
//			}
//			
//		}
//		//記録用戻り値
//		int [] playerSave = {selectF, selectP};
//		return playerSave;
//		
//	}
		
//		switch(selectButtun[i][j]) {
//		case selectP:
//			field[i][j] = 0;
//			break;
//		case selectF:
//			if(selectF == selectP + 3 && field[i][j] != 2) {
//				field[i][j] = 1;
//			}else{
//				System.out.println("その手は選べません。もう一度選択してください。");
//				playerTurn(field, selectButtun, playerCou);
//			}break;
//	}
		
//		//現在地取り消し
//		switch(selectP) {
//			case "1":
//			case "2":
//			case "3":
//				switch(selectF) {
//					case 1:
//					case 4:
//					case 7:
//				
//				}
//				
//		}
//		if(playerCou == 1) {
//			field[0][selectP] = 0;
//		}else {
//			field[playerSave[0]][playerSave[1]] = 0;
//		}
//		//移動後の駒の出現
//		field[selectF][selectP] = 1;
//		
//		//打ち手の記録
//		int[] save = {selectF, selectP};
//		return save;
		
	
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
	
	//試合続行管理メソッド：勝利条件が満たされた場合、試合を止めて結果発表へ　true:誰かが勝利条件を満たした
	public static boolean winLose(int[][]field, int nullCount) {
		
		//プレイヤーの勝利時
		if((field[2][0] == 1 || field[2][1] == 1 || field[2][2] == 1)	//敵陣までプレイヤーが到達した時
//			|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[2][0] == 2 && field[2][2] == 2 && field[1][0] == 1 && field[1][2] == 1))//CPUの打つ手がなくなった時
//			|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[1][0] == 2 && field[1][2] == 2 && field[0][0] == 1 && field[0][2] == 1))
//			|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[1][0] == 2 && field[2][2] == 2 && field[0][0] == 1 && field[1][2] == 1))
//			|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[2][0] == 2 && field[1][2] == 2 && field[1][0] == 1 && field[0][2] == 1))
			|| nullCount > 1000
			|| (field[2][0] != 2 && field[2][1] != 2 && field[2][2] != 2//敵の駒をすべて取った時
			&& field[1][0] != 2 && field[1][1] != 2 && field[1][2] != 2
			&& field[0][0] != 2 && field[0][1] != 2 && field[0][2] != 2)) { 
			return true;
		
		//CPUの勝利時
		}else if((field[0][0] == 2 || field[0][1] == 2 || field[0][2] == 2)	//敵陣までCPUが到達した時
				|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[2][0] == 2 && field[2][2] == 2 && field[1][0] == 1 && field[1][2] == 1))//プレイヤーの打つ手がなくなった時
				|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[1][0] == 2 && field[1][2] == 2 && field[0][0] == 1 && field[0][2] == 1))//二度手間なので書かなくてもいい
				|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[1][0] == 2 && field[2][2] == 2 && field[0][0] == 1 && field[1][2] == 1))
				|| ((field[1][1] == 0 && field[2][1] == 0 && field[0][1] == 0) && (field[2][0] == 2 && field[1][2] == 2 && field[1][0] == 1 && field[0][2] == 1))
				//(field[2][0] - field[1][0] == 1 && field[][] - field[][] == 1)書き換え可能
				|| (field[2][0] != 1 && field[2][1] != 1 && field[2][2] != 1 //敵の駒をすべて取った時
				&&  field[1][0] != 1 && field[1][1] != 1 && field[1][2] != 1
				&&  field[0][0] != 1 && field[0][1] != 1 && field[0][2] != 1)){ 
			return true;
		}else {
			return false;
		}
	}
	
	public static int title() {
		
		//タイトル表示
		System.out.println("    ●　　●  ●●●● 　 ●　　●　    ●●    　 ●●●　 　  ●● 　　  ●　　  ●　  ●　　●");
		System.out.println("   ●　　●  ●　　　 　 　●●       ●　　●　　●　　●　　●　　●   ●  ●  ●　  ●　●●");
		System.out.println("  ●●●●  ●●●●　　  ●●      ●●●●  　●●●●  　●●●●　  ● ●　●　  ●●●●");
		System.out.println(" ●　　●　●　　　　　 ●●　    ●　　●  　●　　　 　●　　●　  ●　   ●　　 ●●　●");
		System.out.println("●　　●　●●●●　 ●　　●　 ●　　●  　●　 　　　●　　●　  ●　   ●　 　●　　●");
		System.out.println();
		
		//ゲームを始める
		System.out.println("はじめる？( \"y\" / \"n\" )");
		System.out.print(">");
		String start = new java.util.Scanner(System.in).nextLine();
		
		int q = 2;//戻り値
		if(start.equals("n")) {
			System.out.println("ゲームを終了します。");
			q = 1;
			return q;
		}else{
			
			String[] rule1 = {
				"== ゲームのルール説明 ==",
				"チェスを簡略化したゲームです。",
				"３×３の盤上で白黒それぞれ三つの駒（ポーン）を使用します。",
				"白（手前） = プレイヤー、 黒(奥) = CPU　で、プレイヤーが先行です",
				" ",
				"== 駒の動き ==",
				"・前のマスに誰もいないときは1マス前に進める",
				"・斜め前に相手の駒があるときは取ることができる。",
				" ",
				"== 勝利条件 ==",
				"・３マス目に自分の駒を進める。",
				"・相手の駒を全て取る",
				"・相手の駒が動けないような配置にする。"
			};
			
			for (String str1 : rule1) {
				System.out.println(str1);
			}
			
			System.out.println();
			System.out.println("次へ [Enter]　");
			String next = new java.util.Scanner(System.in).nextLine();
			
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
			
			for (String str2 : rule2) {
				System.out.println(str2);
			}
			
			System.out.println();
			System.out.println("準備ができたらエンターキーを押してね");
			String go = new java.util.Scanner(System.in).nextLine();
			
			return q; 
			

		}
		
	}
}
//プログラムについて
//プログラムでは、ラベルの貼ってあるマッチ箱とビーズ玉をシミュレートするのではなく、負けに至った手を記録し、その手を打たないようにしています。
//
//具体的には、以下のようにします。
//
//ルール上、可能な手を選びます。
//もし、それが、記録してある負けに至る手であれば他の手を選びます。
//コンピュータが負けた場合には、ビーズを取り除く代わりに、負けに至った最後の手（盤面と駒の動き）を記録します。
//  +---+---+---+
//3 | ○| ○| ○|      7  8  9
//  +---+---+---+
//2 |   |:::|   |   4  5  6
//  +---+---+---+
//1 | ●| ●| ●|      1  2  3
//  +---+---+---+
//    a   b   c
//
//駒を選んでください(1～9)：1
//どこに動かしますか？(1～9)：4
//
//  +---+---+---+
//3 | ○| ○| ○|      7  8  9
//  +---+---+---+
//2 | ●|:::|   |    4  5  6
//  +---+---+---+
//1 |:::| ●| ●|     1  2  3
//  +---+---+---+
//    a   b   c
//
//マッチ箱： b3 を b2 へ
//
//  +---+---+---+
//3 | ○|   | ○|      7  8  9
//  +---+---+---+
//2 | ●| ○|   |      4  5  6
//  +---+---+---+
//1 |:::| ●| ●|      1  2  3
//  +---+---+---+
//    a   b   c
//
//駒を選んでください(1～9)：3
//どこに動かしますか？(1～9)：5
//
//  +---+---+---+
//3 | ○|   | ○|      7  8  9
//  +---+---+---+
//2 | ●| ●|   |      4  5  6
//  +---+---+---+
//1 |:::| ●|:::|     1  2  3
//  +---+---+---+
//    a   b   c
//
//マッチ箱： a3 を b2 へ
//
//  +---+---+---+
//3 |:::|   | ○|     7  8  9
//  +---+---+---+
//2 | ●| ○|   |      4  5  6
//  +---+---+---+
//1 |:::| ●|:::|     1  2  3
//  +---+---+---+
//    a   b   c
//
//駒を選んでください(1～9)：4
//どこに動かしますか？(1～9)：7
//
//  +---+---+---+
//3 | ●|   | ○|      7  8  9
//  +---+---+---+
//2 |   | ○|   |      4  5  6
//  +---+---+---+
//1 |:::| ●|:::|      1  2  3
//  +---+---+---+
//    a   b   c
//
//白の勝ちです。
//終了する場合は'Q'、続ける場合は他のキーを押してください