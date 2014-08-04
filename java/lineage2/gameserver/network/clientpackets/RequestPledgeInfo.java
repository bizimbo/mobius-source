/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package lineage2.gameserver.network.clientpackets;

import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.pledge.Clan;
import lineage2.gameserver.network.serverpackets.PledgeInfo;
import lineage2.gameserver.tables.ClanTable;

/**
 * @author Mobius
 * @version $Revision: 1.0 $
 */
public class RequestPledgeInfo extends L2GameClientPacket
{
	/**
	 * Field _clanId.
	 */
	private int _clanId;
	
	/**
	 * Method readImpl.
	 */
	@Override
	protected void readImpl()
	{
		_clanId = readD();
	}
	
	/**
	 * Method runImpl.
	 */
	@Override
	protected void runImpl()
	{
		Player activeChar = getClient().getActiveChar();
		
		if (activeChar == null)
		{
			return;
		}
		
		if (_clanId < 10000000)
		{
			activeChar.sendActionFailed();
			return;
		}
		
		Clan clan = ClanTable.getInstance().getClan(_clanId);
		
		if (clan == null)
		{
			activeChar.sendActionFailed();
			return;
		}
		
		activeChar.sendPacket(new PledgeInfo(clan));
	}
}
