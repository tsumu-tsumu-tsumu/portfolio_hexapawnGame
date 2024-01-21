package hexapawnRefactor;

import java.util.Random;

public class CpuTurn {
	public static int[] CpuTurn(int[][] field, int[][]selectButtun, int round) {
		
		//表示用配列。CPUが移動したマスをプレイヤーに表示する時に使用。2回使うので、上で宣言しておいて楽をした。
		//（問題文の実行結果が、そのように実装されていたので実装したが個人的に分かりにくいと思う。）
		String [][] cpuField = {{"a1","b1","c1"},{"a2","b2","c2"},{"a3","b3","c3"}};
		
		//CPUの選択
		int[] cpuSelectP = {1, 2, 3, 4, 5, 6, 7, 8, 9};	//移動前のマスの選択を配列で用意。
		
		//配列をランダムにシャッフルさせる。シャッフルした配列を順列で取り出すことで、被りなしでランダムに出力をさせる。
		ShuffleArray(cpuSelectP);
		
		//添え字をループ外に持ち出す用。移動前のマスの位置:field[pi][pj] 移動後のマスの位置：field[fi][fj] 
		int pi = 100; //エラーやバグを防ぐため、0~9以外の整数を代入。なんでもいいです。
		int pj = 100;
		int fi = 100;
		int fj = 100;
		
		//ループ途中離脱用
		boolean isCompleted = false;  //ランダムに選択した手が選択可能だった時:true

	//ポーン移動。条件文の作り方の考え方はプレイヤーの時と同じ。
	//1回目の全件ループで移動前のマス選択を確定し、ポーンを削除。2回目の全件ループで、移動前のマスとの関係から最も有利な手を通過した時にポーンを出現させループを止める。
		
		//選択したポーンを削除
		for(int i = 0; i < field.length; i++) {
			
			for(int j = 0; j < field[0].length; j++) {	

				if(selectButtun[i][j] == cpuSelectP[i] && field[i][j] == 2) {
					pi = i; //配列cpuSelectPの現時点での選択している値の添え字
					pj = j; //移動前のマスの行を確定。
					field[pi][pj] = 0; 
					break; //内側のループを壊す
				}
			}
			
			if(pj != 100) { //手が決まったら外側のループを壊す。
				break;
			}
		}
		
		if(pj == 100) { //疑問：最初の選択は被りなしのランダムなので、ここを通るパターンはないと思っているが、テストだと出てくｊる
			return null;
		}
		
	//ポーンの出現
		//パターン1:移動前マスの斜め前をチェックした時、そこに敵のポーンがあればポーンを出現させる
		for(int i = 0; i < field.length; i++) {
			
			for(int j = 0; j < field[0].length; j++) {	

				switch(cpuSelectP[pi]) {
					case 7:
					case 4:
						if(selectButtun[i][j] == (cpuSelectP[pi] - 2) && field[i][j] == 1) {
							field[i][j] = 2;
							fi = i;
							fj = j;
							isCompleted = true;		
						}
						break;
					case 8:
					case 5:
						if(field[i][j] == 1 && (selectButtun[i][j] == (cpuSelectP[pi] - 2)) ||(field[i][j] == 1 && selectButtun[i][j] == (cpuSelectP[pi] - 4))) {									
							field[i][j] = 2;
							fi = i;
							fj = j;	
							isCompleted = true;		
						}
						break;
					case 9:
					case 6:
						if(field[i][j] == 1 && selectButtun[i][j] == (cpuSelectP[pi] - 4)) {
							field[i][j] = 2;
							fi = i;
							fj = j;	
							isCompleted = true;								
							}
							break;

				}
				
				//この時点でtrueになったら戻り値を返す
				if(isCompleted == true) {
						System.out.println();
						System.out.println("CPU： " + cpuField[pi][pj] + "を" + cpuField[fi][fj] + "へ");
						System.out.println();

							
					//記録用戻り値
					int[] save = {selectButtun[fi][fj], selectButtun[pi][pj]};
					return save;
				}

			}
			
		
			//パターン2:自分のポーンのひとつ前すいているマスをチェックした時、ポーンを出現
			for(int k = 0; k < field.length; k++) {
				
				if(selectButtun[i][k] == (cpuSelectP[pi] - 3) && field[i][k] == 0) {
					field[i][k] = 2;
					fi = i;
					fj = k;	
					isCompleted = true;
					break;
				}
				
			}//内側のfor2
			
			//true:有効打がみつかったら戻り値を返す。
			if(isCompleted == true) {
					System.out.println();
					System.out.println("CPU： " + cpuField[pi][pj] + "を" + cpuField[fi][fj] + "へ");
					System.out.println();
						
				//記録用戻り値
				int[] save = {selectButtun[fi][fj], selectButtun[pi][pj]};
				return save;
				
			}
			
		}//外側のfor2
		
		//全て回して決まらない場合は、nullを返して、hexapawnクラス経由で最初からやり直す。(再帰だと折り返しが多すぎてオーバーフローする。)
		field[pi][pj] = 2;	//1つ目のループ文で選択して削除したポーンを復活させる。
		return null;
		}


//*************************以下メソッド
	
	//配列をシャッフルさせるメソッド
	public static void ShuffleArray(int[] cpuSelect) {
		
		Random random = new Random();	//標準装備のクラスからランダムインスタンスを作成

		int index, temp;
		
		for (int i = cpuSelect.length - 1; i >= 0; i--){ //0の変数を変えた。なぜネットでは入れなかったのか？参考：https://www.choge-blog.com/programming/javaarrayrandomsort/
		    index = random.nextInt(i + 1);	//メソッドを呼び出し  　10未満1以上の乱数を代入。
		    
		    //前後を入れ替え
		    temp = cpuSelect[index];
		    cpuSelect[index] = cpuSelect[i];
		    cpuSelect[i] = temp;
		    
		}

	}

}
