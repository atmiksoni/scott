SELECT
	b.houseId,
	MAX (b.pmcbal) pmcbal,
	MAX (b.warterbal) warterbal,
	MAX (electricbal) electricbal,
	SUM (
		CASE
		WHEN bl.type IN (10)
		AND bl.balanceType = 'PMCBAL' THEN
			bl.amount
		ELSE
			0
		END
	) pmcbalCz,
	SUM (
		CASE
		WHEN bl.type IN (10)
		AND bl.balanceType = 'ELECTRICBAL' THEN
			bl.amount
		ELSE
			0
		END
	) electricbalCz,
	SUM (
		CASE
		WHEN bl.type IN (10)
		AND bl.balanceType = 'WATERBAL' THEN
			bl.amount
		ELSE
			0
		END
	) warterbalCz,
	SUM (
		CASE
		WHEN bl.type IN (0)
		AND bl.balanceType = 'PMCBAL' THEN
			bl.amount
		ELSE
			0
		END
	) pmcbalZc,
	SUM (
		CASE
		WHEN bl.type IN (0)
		AND bl.balanceType = 'ELECTRICBAL' THEN
			bl.amount
		ELSE
			0
		END
	) electricbalZc,
	SUM (
		CASE
		WHEN bl.type IN (0)
		AND bl.balanceType = 'WATERBAL' THEN
			bl.amount
		ELSE
			0
		END
	) warterbalZc
FROM
	T_PMS_Balance b
INNER JOIN T_PMS_BalanceLog bl ON b.balanceId = bl.balanceId
WHERE
	b.communityId = 'A381E66A9F2847E48F357235F9DA5A98'

AND bl.status = 1
GROUP BY
	b.houseId