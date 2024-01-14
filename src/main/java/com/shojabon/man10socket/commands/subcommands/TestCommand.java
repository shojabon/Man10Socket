package com.shojabon.man10socket.commands.subcommands;

import com.shojabon.man10socket.Man10Socket;
import com.shojabon.mcutils.Utils.SItemStack;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class TestCommand implements CommandExecutor {
    Man10Socket plugin;

    public TestCommand(Man10Socket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        JSONObject obj = new JSONObject("{'blockFace': 'WEST', 'item': {'customModelData': -1, 'typeMd5': '6f0c220c269f72ac1eb0f8c0abab1a4b', 'amount': 1, 'lore': [], 'material': 'SHULKER_BOX', 'displayName': 'Shulker Box', 'typeBase64': 'rO0ABXcEAAAAAXNyABpvcmcuYnVra2l0LnV0aWwuaW8uV3JhcHBlcvJQR+zxEm8FAgABTAADbWFw\\r\\ndAAPTGphdmEvdXRpbC9NYXA7eHBzcgA1Y29tLmdvb2dsZS5jb21tb24uY29sbGVjdC5JbW11dGFi\\r\\nbGVNYXAkU2VyaWFsaXplZEZvcm0AAAAAAAAAAAIAAkwABGtleXN0ABJMamF2YS9sYW5nL09iamVj\\r\\ndDtMAAZ2YWx1ZXNxAH4ABHhwdXIAE1tMamF2YS5sYW5nLk9iamVjdDuQzlifEHMpbAIAAHhwAAAA\\r\\nBHQAAj09dAABdnQABHR5cGV0AARtZXRhdXEAfgAGAAAABHQAHm9yZy5idWtraXQuaW52ZW50b3J5\\r\\nLkl0ZW1TdGFja3NyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2\\r\\nYS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAySdAALU0hVTEtFUl9CT1hzcQB+AABzcQB+AAN1\\r\\ncQB+AAYAAAAEcQB+AAh0AAltZXRhLXR5cGV0AAhpbnRlcm5hbHQADWJsb2NrTWF0ZXJpYWx1cQB+\\r\\nAAYAAAAEdAAISXRlbU1ldGF0AAtUSUxFX0VOVElUWXQI9Eg0c0lBQUFBQUFBQS8rVllXMndVWlJU\\r\\nK3Q3dHR0eGRBRUlNeFJza20raUtRaUJxeE1RR0VJazI4UEdCOU1iait1L04zZCtqc3pETHpiMHR0\\r\\nbWpBenBmZmFBcVZWa1JSdHVaYVdKa1VvQlN3K1lqUlJuM2p4QVgrMnRBK0dONlBSQk0vTTdzN2V0\\r\\nMXVxSUxEWmg1M1ovM0xPK2M3NXpxVVVvVkswOUExQjh0WldpcFNuRGU5aG54TVY4Qng2SXNDTHhD\\r\\ndmpHbHFoK0VOQ0xaSGRIbWxQQ1Nxc29pU2dsQ0tFbHRpUVk0Y2dVUlRac0RLK2daY2wwYTNVU3pK\\r\\nblE0VmJwSkJJYmFYSVRySFBqa3EyOHh6WkptQ2ZBaWM0N2Fob0t3NWdINEVIVklMS0swV3ZINHMw\\r\\nUUVScVhtRlBrNldHbDRrYkswSGlwUVhJTHRRSnlCYTlmM2w4a1NCSmxCZDkwUVdPNklMSEV6WENj\\r\\nbEFraWhKZFVnaW5sRldMSHBuZ1d1d1JDSWhielBGS1VNQU5UdVI0QndlSXpkSG9JbnVvakYwVkh6\\r\\nUzZQSkxBdVNxb0hDSnJYRHpGQXU5MVZkUmdRWUhIa01nUldZQ2JPT3VWUW1XK2xsQy9MSVY4ZnV1\\r\\ndDVLa0pLVjVNRXhaNkpVR1NYUlV1dkR1RVhXdGNGQzZFcC9kWHU1clc1THd6dHM4bjQ0YjR2clVM\\r\\nMkVlSUdOOVlHU0F5RnJnZEJvQ3VwcDNXZTFkVENYSzhKY25FQ2RBVW9IUHBGb21lK3grWVJPQjlm\\r\\ndW9PaHVTZ1FPS1N6blFjdW5Yc0pGTUhtSHFjcVQxTTYyTHE0TTJwS2FaT01uWHNWdnZGOEw3SlpB\\r\\nM1ExZitCMU0yRHMyZU9nSUJ6bzMwekU0ZVkyaFVlMzI4cWNaU3A4S1BUL0E2Wk9qVXpyVE5GQXp0\\r\\nYXRpV2tVQ253dHNRUllTdW0yQWhHaEtMaGFFc0xSeDlvNllaNGdQK2k0VmdRQ2NjMEwwYzM3cHVY\\r\\nL3pyUWtyZS9aallyMHk0eWJacnBsNW4yTmRNUE1lMUxwZzB6N2F1c0xteERKK1oxaG9nYy80YTZI\\r\\nSlpyM2NrNnowNjB6a3oxM3VvYm11azVQTmU2SDJRMUpOWmJtSDQwSDh6TExjd0w4c0RjbGczejYv\\r\\nY1E4d1lpQ0ZMOTNhRnVxSlFKYmIyTjZmdE4yS2NlV3FqTExLanRpd252WHg0UXFMTUZPT0E5d2JU\\r\\nelRPOHdyZGYyME9MdHRQQjJMQ2EwcjkxRHZKT0xqNFdnTFpQRXVOWkhtSDRRd3ZtaHhiYll3clp3\\r\\nTWRqK2ZDK3hUYUxldThkV0d6RUp1NXZwcHcycjZSTVBMY2dsRnNoRitiZEhwY2hSajBYT2pvcnJp\\r\\nS3p3a21oNGl4TTVvZU1DNUNsQlpUV3lwRkMzUjhBY2NhQ2xBU3hpTitVRFJLRTRFSVFiTzYvdmV2\\r\\nc3ZjQkRlQzN1WHh1OXBmUG1scHRRV0o2bjNTbW5NTnBhZ0Zac3BXTkVUb2dUVTRtdDRFQ2pha1VX\\r\\nYnZnRG1SV2pXdUNKVXREbGdLTEhwU1lTaVo3MGJoQTZDUmhTQTNPV29ycTdhQ3I4YzIxZTFWcjE2\\r\\ndW1wbit4K0R0THI5YkYvTWwxZFU3Zzd4UWFQdFd4Mjd6WW1XV0JLWWE1NU5pQklpRXBuM3JzT1VZ\\r\\nbSt0bTR1b2tWMnk2ZWFQYjd3NXRySXhoMlNUdnR1L1Q5UnZ1emE1NGNLNm5kT2xyeTlFc3RYcGtn\\r\\nV2tPbUpzY2tPblNyZ2NvbDFGOHhydDBvNjEvUjkrVzFsNTRPemZ2dS8rM1BEVFFrUjdKcXZSSW9L\\r\\nbDAwcjNmTFFTZVVxS0hJOFFTa2pUYzErY21CbS9rSlVna3Zjblo0MXdXM09PaHZPanhsU1dpVXVh\\r\\nNWJ6V2JyT0xHamFidzlNbStaeGkyb0dadm0vZ1piaDlLQ1dVUXd1L29lWEN6UGdBSEJudW5RejNm\\r\\nQUpkWExodDcrelVDSFNnNGVIMmNGYy9VNkhCT3d4ZFhMampzSEZ6bHdvMzU4TWdMMWdNVXJ5WU5Q\\r\\nSERBNUVtVW1yN1I2R2tMN1R3ZGFiaFd5L0JRdEh0bDBqdUFWcDVFb25QTjhOQy9mZXR1NThkUFI0\\r\\nZSs1U3BZK0hKSytIUjNoeWdqdDMvK1F4VEQ1cVRwQlBoWGdoWnpaRDk3Q0NJZi91eUN0OThzSDNP\\r\\nd3JZa2d1MnFoT0Vrd2RSdlRsTWxxdVJUQUpTaVpVRFdnZ0FCWHdka0w4a05kdlFZSjRVQVkvZXVV\\r\\nQ0RvcHBEL0RkdlpVVm5DYXpPdEpCWVB5eFAzbUxlalhPNlRXaEtrdTFOWElsUlpBanUxQzJUYUpO\\r\\nT0hqRFpPN3pPYnVYR205NXU4M016MFN5WmRaRGtwWlZ6VWZ6U0hFL1hrd2VOcGt1bDdUVkZHRWtR\\r\\nY01BWEt6UHhNTlZ5RXFkM0E3VXhyWTJvTFU0SDJvYkR0bkR0eUxzVkxMSDhvelZ3Tlp1THlEUFB3\\r\\nOU5sZUtJSllmTnB0UzYzbE12REFoc2E0MGdOTVAyUE95U1pNOXBvQUJST1pJWW1nazlQeGV0Zzlh\\r\\nZ0JvN29zTllZMkJKVk5QUXR3d3JaMnBiYVoxWW1lalY4QnNIU1pad3JvelRGY05TOGYrTm95bm5n\\r\\nSXIzcHdhQms2MXJPdWFqMERMN3BwQUhia2lJR3NSWEJDcjV4eCtndU8xM01hTG54bWZ6M1BVY25k\\r\\nKzI3Y2JvWnJ6UU4ydjNibXozaXFBbjQ1Vlo3V2k1SzMxR0FXYVRCUWU2bnJSUzlLcXV1ZlRxN3BN\\r\\nKzFCbU1UZk5YM0pHeFBROUZSSHp4ZTlqWWk2eGlrZzVJS1ZYbTZzeVZKdkd3Z3hsSnBkbktrckt2\\r\\nNmxFUFRmYUZ6NEdiak1FS1dYZXhPTFBnNjJ5NVFPb0dRK0NkMGJuOVpwMjg4cTA0ZHJxRVlqNlBG\\r\\nTkNoZVd0NVlzcDUzNThJQ1k2eVZ6OXFNemppNHdvK2djdStxVlJVaDBBQUE9PXEAfgAR\\r\\n'}, 'interactionPoint': {'world': 'world', 'x': 134, 'y': 80.92272996902466, 'z': 24.29465162754059, 'pitch': 0, 'yaw': 0}, 'clickedBlockMaterial': 'SHULKER_BOX', 'clickedBlock': {'world': 'world', 'x': 134, 'y': 80, 'z': 24, 'pitch': 0, 'yaw': 0}, 'action': 'RIGHT_CLICK_BLOCK', 'player': 'ffa9b4cb-ada1-4597-ad24-10e318f994c8', 'hand': 'HAND'}");

        for(int i = 0; i < 10000; i++){
            Man10Socket.sendEvent("player_interact", obj);
        }
        return true;
    }
}